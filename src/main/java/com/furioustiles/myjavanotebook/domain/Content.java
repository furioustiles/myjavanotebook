package com.furioustiles.myjavanotebook.domain;

import com.furioustiles.myjavanotebook.util.ProblemMetadata;
import java.util.Date;
import java.util.List;

/**
 * Model which wraps a list of all problems in the directory.
 *
 * @author furioustiles
 */
public class Content {

  private List<ProblemMetadata> contentBody;
  private Date timestamp;

  public void setContentBody(List<ProblemMetadata> contentBody) {
    this.contentBody = contentBody;
  }

  public List<ProblemMetadata> getContentBody() {
    return this.contentBody;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public Date getTimestamp() {
    return this.timestamp;
  }
}
