package com.furioustiles.myjavanotebook.commands;

import java.util.List;

/**
 * Wrapper class for containing test case information.
 *
 * @author furioustiles
 */
public class TestCase {

  private String id;
  private List<String> parameterValues;

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return this.id;
  }

  public void setParameterValues(List<String> parameterValues) {
    this.parameterValues = parameterValues;
  }

  public List<String> getParameterValues() {
    return this.parameterValues;
  }
}
