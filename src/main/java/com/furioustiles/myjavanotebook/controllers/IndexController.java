package com.furioustiles.myjavanotebook.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller which handles rendering the index HTML template.
 *
 * @author furioustiles
 */
@Controller
public class IndexController {

  /**
   * Renders the index template.
   */
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String index() {
    return "index";
  }
}
