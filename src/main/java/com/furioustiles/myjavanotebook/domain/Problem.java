package com.furioustiles.myjavanotebook.domain;

import com.furioustiles.myjavanotebook.util.Cell;
import java.util.Date;
import java.util.List;

/**
 * An entity that represents all problem information such as the source code and the main function
 * signature (the function which will ultimately return the solution).
 *
 * @author furioustiles
 */
public class Problem {

  private String fileName;
  private String displayName;
  private List<String> tags;
  private Date timeCreated;
  private Date lastModified;
  private String problemSourceURL;
  private String returnType;
  private String mainFunctionSignature;
  private List<Cell> cells;

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileName() {
    return this.fileName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return this.displayName;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public List<String> getTags() {
    return this.tags;
  }

  public void setTimeCreated(Date timeCreated) {
    this.timeCreated = timeCreated;
  }

  public Date getTimeCreated() {
    return this.timeCreated;
  }

  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  public Date getLastModified() {
    return this.lastModified;
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

  public void setCells(List<Cell> cells) {
    this.cells = cells;
  }

  public List<Cell> getCells() {
    return this.cells;
  }
}
