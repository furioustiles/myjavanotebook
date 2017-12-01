package com.furioustiles.myjavanotebook.util;

import com.furioustiles.myjavanotebook.util.Cell;
import com.furioustiles.myjavanotebook.util.CellType;
import com.furioustiles.myjavanotebook.util.FilesManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for building a solution string
 *
 * @author furioustiles
 */
public class SolutionBuilder {

  private String sourceCode;
  private List<Cell> cells;
  private String returnType;
  private String mainFunctionSignature;
  private List<String> parameterValues;

  public SolutionBuilder(List<Cell> cells, String returnType, String mainFunctionSignature,
      List<String> parameterValues) {
    setCells(cells);
    setReturnType(returnType);
    setMainFunctionSignature(mainFunctionSignature);
    setParameterValues(parameterValues);
  }

  private String getTemplateString(String sourceCodeString, String paramInitString,
      String paramNamesString, String printMethodString) {
    StringBuilder solnStringBuilder = new StringBuilder();

    // import statements
    solnStringBuilder.append("import java.io.*;import java.util.*;import java.math.*;");

    // ListNode class definition for linkedlist problems
    solnStringBuilder.append("class ListNode{int val;ListNode next;ListNode(int x){val=x;}}");

    // TreeNode class definition for binary tree problems
    solnStringBuilder.append("class TreeNode{int val;TreeNode left;TreeNode right;");
    solnStringBuilder.append("TreeNode(int x){val=x;}}");

    // Main java class
    solnStringBuilder.append("public class Solution{");
    solnStringBuilder.append(sourceCodeString);

    // method to print solution
    solnStringBuilder.append("public static void printResult(");
    solnStringBuilder.append(this.returnType);
    solnStringBuilder.append(" res){");
    solnStringBuilder.append(printMethodString);
    solnStringBuilder.append("}");

    // main function definition
    solnStringBuilder.append("public static void main(String[] args){");
    solnStringBuilder.append("Solution soln=new Solution();");
    solnStringBuilder.append(paramInitString);
    solnStringBuilder.append("printResult(soln.");
    solnStringBuilder.append(this.mainFunctionSignature);
    solnStringBuilder.append("(");
    solnStringBuilder.append(paramNamesString);
    solnStringBuilder.append("));}}");

    return solnStringBuilder.toString();
  }

  private List<String> getParamTypes(String codeString) {
    List<String> paramTypes = new ArrayList<>();
    StringBuilder paramStringBuilder = new StringBuilder();
    String mfs = this.mainFunctionSignature + "(";
    int currIndex = codeString.indexOf(this.mainFunctionSignature);

    // iterate forward until "(" token is reached
    while (codeString.charAt(currIndex) != '(') {
      currIndex++;
    }

    // iterate forward, while collecting characters, until ")" is reached
    while (codeString.charAt(currIndex) != ')') {
      currIndex++;
      paramStringBuilder.append(codeString.charAt(currIndex));
    }

    // split paramString by comma, and parse for the first word in each element in list
    String[] paramPairs = paramStringBuilder.toString().split(",");
    for (String paramPair : paramPairs) {
      String trimmedParamPair = paramPair.trim();
      String[] paramPairArray = trimmedParamPair.split(" ");
      paramTypes.add(paramPairArray[0]);
    }

    return paramTypes;
  }

  private String paramAssignmentString(String paramType, String paramValue) {
    String constructorTemplate = new String("new %s %s");
    StringBuilder paramAssignmentBuilder = new StringBuilder();

    switch (paramType) {
      case "int":
      case "long":
      case "double":
      case "char":
      case "boolean":
      case "String":
        paramAssignmentBuilder.append(paramValue);
        break;
      case "int[]":
      case "int[][]":
      case "long[]":
      case "long[][]":
      case "double[]":
      case "double[][]":
      case "char[]":
      case "char[][]":
      case "boolean[]":
      case "boolean[][]":
      case "String[]":
      case "String[][]":
        paramAssignmentBuilder.append(String.format(constructorTemplate, paramType,
            paramValue.replace("[", "{").replace("]", "}")));
        break;
      default:
    }



    return paramAssignmentBuilder.toString();
  }

  private String createSourceCodeString() {
    StringBuilder sourceCodeStringBuilder = new StringBuilder();
    for (Cell c : this.getCells()) {
      CellType cellType = c.getCellType();
      if (cellType == CellType.CODE) {
        sourceCodeStringBuilder.append(c.getCellContent());
        sourceCodeStringBuilder.append('\n');
      }
    }

    return sourceCodeStringBuilder.toString();
  }

  private String createParamInitString(List<String> paramTypes) {
    StringBuilder paramInitBuilder = new StringBuilder();
    String template = new String("%s %s = %s;");

    for (int i = 0; i < paramTypes.size(); i++) {
      String argName = "arg"+Integer.toString(i);
      String paramType = paramTypes.get(i);
      paramInitBuilder.append(String.format(template, paramType, argName,
          paramAssignmentString(paramType, this.parameterValues.get(i))));
    }

    return paramInitBuilder.toString();
  }

  private String createParamNamesString(int paramCount) {
    StringBuilder paramNamesBuilder = new StringBuilder();

    // naming convention: arg0, arg1, ..., argN-1
    for (int i = 0; i < paramCount; i++) {
      String argNameString = "arg"+Integer.toString(i);
      paramNamesBuilder.append(argNameString);
      if (i < paramCount-1) {
        paramNamesBuilder.append(","); // append a comma after each arg name except the last one
      }
    }

    return paramNamesBuilder.toString();
  }

  private String createPrintMethodString() {
    StringBuilder printMethodBuilder = new StringBuilder();
    switch (this.returnType) {
      case "int":
      case "long":
      case "double":
      case "char":
      case "boolean":
      case "String":
        printMethodBuilder.append("System.out.println(res);");
        break;
      case "int[]":
      case "long[]":
      case "double[]":
      case "char[]":
      case "boolean[]":
      case "String[]":
        printMethodBuilder.append("System.out.println(Arrays.toString(res));");
        break;
      case "int[][]":
      case "long[][]":
      case "double[][]":
      case "char[][]":
      case "boolean[][]":
      case "String[][]":
        printMethodBuilder.append("System.out.println(Arrays.toDeepString(res));");
        break;
      case "ListNode":
        printMethodBuilder.append("System.out.println(\"Method is not implemented (yet)\");");
        break;
      case "TreeNode":
        printMethodBuilder.append("System.out.println(\"Method is not implemented (yet)\");");
        break;
      default:
        printMethodBuilder.append("System.out.println(\"Method is not implemented\");");
    }

    return printMethodBuilder.toString();
  }

  public String getSolutionString() {
    String sourceCodeString = createSourceCodeString();

    List<String> paramTypes = getParamTypes(sourceCodeString);
    String paramInitString = createParamInitString(paramTypes);
    String paramNamesString = createParamNamesString(paramTypes.size());

    String printMethodString = createPrintMethodString();

    return getTemplateString(sourceCodeString, paramInitString, paramNamesString,
        printMethodString);
  }

  public void setCells(List<Cell> cells) {
    this.cells = cells;
  }

  public List<Cell> getCells() {
    return this.cells;
  }

  public void setReturnType(String returnType) {
    this.returnType = returnType;
  }

  public String getReturnType() {
    return this.returnType;
  }

  public void setMainFunctionSignature(String mainFunctionSignature) {
    this.mainFunctionSignature = mainFunctionSignature;
  }

  public String getMainFunctionSignature() {
    return this.mainFunctionSignature;
  }

  public void setParameterValues(List<String> parameterValues) {
    this.parameterValues = parameterValues;
  }

  public List<String> getParameterValues() {
    return this.parameterValues;
  }
}
