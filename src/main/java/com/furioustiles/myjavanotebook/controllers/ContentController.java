package com.furioustiles.myjavanotebook.controllers;

import com.furioustiles.myjavanotebook.domain.Content;
import com.furioustiles.myjavanotebook.services.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller responsible for delivering content in the problem directory.
 *
 * @author furioustiles
 */
 @RestController
 public class ContentController {

  private ContentService contentService;

  @Autowired
  public void setContentService(ContentService contentService) {
    this.contentService = contentService;
  }

  /**
   * Handles HTTP GET requests for retrieving all directory content.
   *
   * @return A Content object which contains directory content information.
   */
  @RequestMapping(value = "/api/content", method = RequestMethod.GET)
  public Content getAllContent() {
    return contentService.getDirectoryContent();
  }

  /**
   * Handles HTTP GET reqeusts for retrieving directory content by tag.
   *
   * @param tag Search tag to return subset of content
   * @return A Content object which contains directory content information by given tag
   */
  @RequestMapping(value = "/api/content/tagged", method = RequestMethod.GET)
  public Content getContentByTag(@RequestParam("tag") String tag) {
    return contentService.getDirectoryContentByTag(tag);
  }

  /**
   * Handles HTTP POST requests for uploading an external file to the content directory.
   *
   * @param jsonString String containing file data to save
   */
  @RequestMapping(value = "/api/content/upload", method = RequestMethod.POST)
  public void uploadContent(@RequestParam("fileString") String jsonString) {
    contentService.uploadContent(jsonString);
  }
}
