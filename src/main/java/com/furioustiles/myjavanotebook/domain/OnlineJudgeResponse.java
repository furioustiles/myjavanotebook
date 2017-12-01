package com.furioustiles.myjavanotebook.domain;

/**
 * Wrapper class for containing a result string, a String representing the output from the online
 * judge.
 *
 * @author furioustiles
 */
public class OnlineJudgeResponse {

  private String resultString;

  public void setResultString(String resultString) {
    this.resultString = resultString;
  }

  public String getResultString() {
    return this.resultString;
  }
}
