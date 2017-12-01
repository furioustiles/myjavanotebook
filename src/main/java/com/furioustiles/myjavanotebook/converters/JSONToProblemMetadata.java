package com.furioustiles.myjavanotebook.converters;

import com.furioustiles.myjavanotebook.util.ProblemMetadata;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter for creating ProblemMetadata objects out of JSONObjects.
 *
 * @author furioustiles
 */
@Component
public class JSONToProblemMetadata implements Converter<JSONObject, ProblemMetadata> {

  private JSONObject jsonObject;

  private String convertFileName() {
    return (String) this.jsonObject.get("fileName");
  }

  private String convertDisplayName() {
    return (String) this.jsonObject.get("displayName");
  }

  private List<String> convertTags() {
    ArrayList<String> tags = new ArrayList<>();
    JSONArray jsonTagArray = (JSONArray) this.jsonObject.get("tags");
    Iterator jsonTagIterator = jsonTagArray.iterator();

    while (jsonTagIterator.hasNext()) {
      String tag = (String) jsonTagIterator.next();
      tags.add(tag);
    }

    return tags;
  }

  private Date convertTimeCreated() {
    long timeCreatedValue = (long) this.jsonObject.get("timeCreated");
    Date timeCreated = new Date(timeCreatedValue);

    return timeCreated;
  }

  @Override
  public ProblemMetadata convert(JSONObject jsonObject) {
    ProblemMetadata problemMetadata = new ProblemMetadata();
    this.jsonObject = jsonObject;

    problemMetadata.setFileName(convertFileName());

    problemMetadata.setDisplayName(convertDisplayName());

    problemMetadata.setTags(convertTags());

    problemMetadata.setTimeCreated(convertTimeCreated());

    return problemMetadata;
  }
}
