package com.furioustiles.myjavanotebook.services;

import com.furioustiles.myjavanotebook.converters.JSONToProblemMetadata;
import com.furioustiles.myjavanotebook.domain.Content;
import com.furioustiles.myjavanotebook.util.FilesManager;
import com.furioustiles.myjavanotebook.util.ProblemMetadata;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the business logic for delivering directory content from
 * the filesystem.
 *
 * @author furioustiles
 */
@Service
public class ContentService {

  private FilesManager filesManager;
  private JSONToProblemMetadata converter;

  @Autowired
  public ContentService() {
    this.filesManager = new FilesManager();
    this.converter = new JSONToProblemMetadata();
  }

  /**
   * Business logic for retrieving a Content wrapper object.
   *
   * @return A Content object containing all directory content
   */
  public Content getDirectoryContent() {
    List<JSONObject> problemJSONList = filesManager.getDirectoryJSONContent();
    List<ProblemMetadata> problemMetadataList = new ArrayList<>();

    for (JSONObject obj : problemJSONList) {
      try {
        ProblemMetadata problemMetadata = converter.convert(obj);
        problemMetadataList.add(problemMetadata);
      } catch (Exception ex) {
        continue;
      }
    }
    Collections.sort(problemMetadataList, Collections.reverseOrder());

    Content dirContent = new Content();
    dirContent.setContentBody(problemMetadataList);
    dirContent.setTimestamp(new Date());

    return dirContent;
  }

  /**
   * Business logic for retrieving Content wrapper for tag.
   *
   * @param tag Search tag to return subset of content
   * @return A Content object containing all directory content relevant to tag
   */
  public Content getDirectoryContentByTag(String tag) {
    if (tag.equals("")) {
      return this.getDirectoryContent();
    }

    List<JSONObject> problemJSONList = filesManager.getDirectoryJSONContent();
    List<ProblemMetadata> problemMetadataList = new ArrayList<>();

    for (JSONObject obj : problemJSONList) {
      try {
        ProblemMetadata problemMetadata = converter.convert(obj);
        List<String> tags = problemMetadata.getTags();
        if (tags.contains(tag)) {
          problemMetadataList.add(problemMetadata);
        }
      } catch (Exception ex) {
        continue;
      }
    }
    Collections.sort(problemMetadataList, Collections.reverseOrder());

    Content dirContent = new Content();
    dirContent.setContentBody(problemMetadataList);
    dirContent.setTimestamp(new Date());

    return dirContent;
  }

  /**
   * Business logic for uploading new problem to content directory.
   *
   * @param jsonString String containing file data to save
   */
  public void uploadContent(String jsonString) {
    JSONParser parser = new JSONParser();
    try {
      JSONObject problemJSON = (JSONObject) parser.parse(jsonString);
      String fileName = (String) problemJSON.get("fileName");

      problemJSON.remove("timeCreated");
      problemJSON.put("timeCreated", new Date().getTime());
      problemJSON.remove("lastModified");
      problemJSON.put("lastModified", new Date().getTime());

      this.filesManager.writeProblemJSON(problemJSON, fileName);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
