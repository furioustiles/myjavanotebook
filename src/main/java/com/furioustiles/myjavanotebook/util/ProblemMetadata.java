package com.furioustiles.myjavanotebook.util;

import java.lang.Comparable;
import java.util.Date;
import java.util.List;

/**
 * A wrapper for basic problem information, such as the name of the problem and problem tags.
 *
 * @author furioustiles
 */
public class ProblemMetadata implements Comparable<ProblemMetadata> {

  private String fileName;
  private String displayName;
  private List<String> tags;
  private Date timeCreated;

  /**
   * Override of the compareTo method from the Comparable interface which sorts ProblemMetadata
   * objects by the time they were created.
   *
   * @return The value 0 if dates are equal, 1 if this date is after the compared date, and -1 if
   * this date is before the compared date.
   */
  @Override
  public int compareTo(ProblemMetadata other) {
    Date otherTimeCreated = other.getTimeCreated();
    if (this.timeCreated.equals(otherTimeCreated)) {
      return 0;
    }
    return this.timeCreated.after(otherTimeCreated) ? 1 : -1;
  }

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
}
