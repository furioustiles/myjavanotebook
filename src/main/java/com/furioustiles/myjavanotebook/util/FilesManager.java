package com.furioustiles.myjavanotebook.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Responsible for all the file operations in the filesystem API (opening, saving, and deleting
 * problem files).
 *
 * @author furioustiles
 */
public class FilesManager {

  private File problemDirectory;

  /**
   * Constructor which initializes a File object for where the problem directory will be located
   * (default ~/.myjavanotebook).
   */
  public FilesManager() {
    String homeDirectory = System.getProperty("user.home").toString();
    String contentDirectory = new String(".myjavanotebook");
    this.problemDirectory = new File(homeDirectory, contentDirectory);
  }

  private void validateProblemDirectoryExists() {
    if (!problemDirectory.exists()) {
      problemDirectory.mkdir();
    }
  }

  /**
   * API call that opens valid JSON files and converts them to JSONObjects.
   *
   * @return A list of JSONObjects to be converted to directory content
   */
  public List<JSONObject> getDirectoryJSONContent() {
    this.validateProblemDirectoryExists();

    List<JSONObject> problemJSONList = new ArrayList<>();
    File[] fileList = this.problemDirectory.listFiles();

    for (int i = 0; i < fileList.length; i++) {
      problemJSONList.add(openProblemJSON(fileList[i].getName()));
    }

    return problemJSONList;
  }

  /**
   * Opens a file by name and returns its JSON content.
   *
   * @param fileName Name of file to be opened
   * @return JSONObject to be converted to a Problem
   */
  public JSONObject openProblemJSON(String fileName) {
    this.validateProblemDirectoryExists();

    JSONParser parser = new JSONParser();
    File toOpen = new File(this.problemDirectory, fileName);
    JSONObject problemJSON = new JSONObject();

    try {
      FileReader jsonFileReader = new FileReader(toOpen.toString());
      problemJSON = (JSONObject) parser.parse(jsonFileReader);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return problemJSON;
  }

  /**
   * Opens the newest problem JSON object.
   *
   * @return The newest problem JSON object
   */
  public JSONObject openNewestProblemJSON() {
    this.validateProblemDirectoryExists();

    JSONObject newestProblemJSON = new JSONObject();
    long maxTime = 0L;
    File[] fileList = this.problemDirectory.listFiles();

    for (int i = 0; i < fileList.length; i++) {
      JSONObject obj = openProblemJSON(fileList[i].getName());
      long timeCreatedValue = (long) obj.get("timeCreated");

      if (timeCreatedValue > maxTime) {
        newestProblemJSON = obj;
        maxTime = timeCreatedValue;
      }
    }

    return newestProblemJSON;
  }

  /**
   * Writes a Problem object to the filesystem.
   *
   * @param problemJSONObject JSON object to be saved onto the filesystem
   * @param fileName Name of file to be written
   */
  public void writeProblemJSON(JSONObject problemJSONObject, String fileName) {
    File filePath = new File(this.problemDirectory, fileName);

    try (FileWriter fileWriter = new FileWriter(filePath.toString())) {
      fileWriter.write(problemJSONObject.toJSONString());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Deletes a file by name.
   *
   * @param fileName Name of file to be deleted
   */
  public void removeFile(String fileName) {
    File toDelete = new File(this.problemDirectory, fileName);
    toDelete.delete();
  }

  /**
   * Saves a string containing java source code to the /tmp directory as a java class file, then
   * compiles and runs that file.
   *
   * @param sourceCodeString Code to be saved to the temporary directory
   * @param className Name of the class to be compiled and ran
   * @return The output of the program in the form of a String
   */
  public String runProcess(String sourceCodeString, String className) {
    String tmpPath = System.getProperty("java.io.tmpdir");
    String sourceFileName = className + ".java";
    String solutionClassPath = new File(tmpPath, sourceFileName).toString();

    // Save to tmp directory
    try (PrintWriter printWriter = new PrintWriter(solutionClassPath)) {
      printWriter.println(sourceCodeString);
    } catch (Exception ex) {
      StringWriter exceptionStringWriter = new StringWriter();
      ex.printStackTrace(new PrintWriter(exceptionStringWriter));
      return exceptionStringWriter.toString();
    }

    // Compile source
    try {
      ProcessBuilder compileProcessBuilder = new ProcessBuilder("javac", sourceFileName);
      compileProcessBuilder.directory(new File(tmpPath));
      Process compileProcess = compileProcessBuilder.start();

      // Check if error stream is empty
      BufferedReader compileErrorBufferedReader = new BufferedReader(new InputStreamReader(
          compileProcess.getErrorStream()));
      String compileErrorLine;
      StringBuilder errorStringBuilder = new StringBuilder();
      while ((compileErrorLine = compileErrorBufferedReader.readLine()) != null) {
        errorStringBuilder.append(compileErrorLine);
      }
      if (!errorStringBuilder.toString().isEmpty()) {
        return errorStringBuilder.toString();
      }

      compileProcess.waitFor();
    } catch (Exception ex) {
      StringWriter exceptionStringWriter = new StringWriter();
      ex.printStackTrace(new PrintWriter(exceptionStringWriter));
      return exceptionStringWriter.toString();
    }

    // Run Java class
    StringBuilder resultStringBuilder = new StringBuilder();
    try {
      ProcessBuilder javaProcessBuilder = new ProcessBuilder("java", className);
      javaProcessBuilder.directory(new File(tmpPath));
      Process javaProcess = javaProcessBuilder.start();

      // Check if error stream is empty
      BufferedReader runtimeErrorBufferedReader = new BufferedReader(new InputStreamReader(
          javaProcess.getErrorStream()));
      String runtimeErrorLine;
      StringBuilder runtimeStringBuilder = new StringBuilder();
      while ((runtimeErrorLine = runtimeErrorBufferedReader.readLine()) != null) {
        runtimeStringBuilder.append(runtimeErrorLine);
      }
      if (!runtimeStringBuilder.toString().isEmpty()) {
        return runtimeStringBuilder.toString();
      }

      // Receive stdout stream
      BufferedReader resultBufferedReader = new BufferedReader(new InputStreamReader(
          javaProcess.getInputStream()));
      String resultLine;
      while ((resultLine = resultBufferedReader.readLine()) != null) {
        resultStringBuilder.append(resultLine);
      }

      javaProcess.waitFor();
      resultBufferedReader.close();
    } catch (Exception ex) {
      StringWriter exceptionStringWriter = new StringWriter();
      ex.printStackTrace(new PrintWriter(exceptionStringWriter));
      return exceptionStringWriter.toString();
    }

    return resultStringBuilder.toString();
  }
}
