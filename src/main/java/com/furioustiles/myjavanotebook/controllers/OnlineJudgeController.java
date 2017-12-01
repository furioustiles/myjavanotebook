package com.furioustiles.myjavanotebook.controllers;

import com.furioustiles.myjavanotebook.commands.TestCase;
import com.furioustiles.myjavanotebook.domain.OnlineJudgeResponse;
import com.furioustiles.myjavanotebook.services.OnlineJudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller responsible for running the OnlineJudge service through running a solution for a
 * given problem.
 *
 * @author furioustiles
 */
@RestController
public class OnlineJudgeController {

  private OnlineJudgeService onlineJudgeService;

  @Autowired
  public void setOnlineJudgeService(OnlineJudgeService onlineJudgeService) {
    this.onlineJudgeService = onlineJudgeService;
  }

  /**
   * Handles HTTP POST requests for running solution code with given parameters.
   *
   * @param testCase Wrapper form containing parameter values and the file containing the code to
   * run
   * @return An OnlineJudgeResponse object containing the output from the online judge
   */
  @RequestMapping(value = "/api/run", method = RequestMethod.POST)
  public OnlineJudgeResponse runSolution(@RequestBody TestCase testCase) {
    return this.onlineJudgeService.getResult(testCase);
  }
}
