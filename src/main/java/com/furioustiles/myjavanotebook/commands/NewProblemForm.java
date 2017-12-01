package com.furioustiles.myjavanotebook.commands;

/**
 * Wrapper class for submitting new problems as an HTTP POST request.
 *
 * @author furioustiles
 */
public class NewProblemForm {

  private String displayName;
  private String tagString;
  private String problemSourceURL;
  private String returnType;
  private String mainFunctionSignature;

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public void setTagString(String tagString) {
    this.tagString = tagString;
  }

  public String getTagString() {
    return this.tagString;
  }

  public void setProblemSourceURL(String problemSourceURL) {
    this.problemSourceURL = problemSourceURL;
  }

  public String getProblemSourceURL() {
    return this.problemSourceURL;
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
}
