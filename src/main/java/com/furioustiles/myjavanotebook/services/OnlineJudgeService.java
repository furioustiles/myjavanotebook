package com.furioustiles.myjavanotebook.services;

import com.furioustiles.myjavanotebook.commands.TestCase;
import com.furioustiles.myjavanotebook.converters.JSONToCell;
import com.furioustiles.myjavanotebook.domain.OnlineJudgeResponse;
import com.furioustiles.myjavanotebook.util.Cell;
import com.furioustiles.myjavanotebook.util.CellType;
import com.furioustiles.myjavanotebook.util.FilesManager;
import com.furioustiles.myjavanotebook.util.SolutionBuilder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the business logic for an online judge.
 *
 * @author furioustiles
 */
@Service
public class OnlineJudgeService {

  private FilesManager filesManager;

  @Autowired
  public OnlineJudgeService() {
    this.filesManager = new FilesManager();
  }

  /**
   * Business logic for running the online judge and returning the output from the given program
   * and test case.
   *
   * @param testCase TestCase object for all testcase information, including the file name and
   * parameter values
   * @return A response to return to the OnlineJudgeController containing the output of the user's
   * program as a String
   */
  public OnlineJudgeResponse getResult(TestCase testCase) {
    OnlineJudgeResponse onlineJudgeRes = new OnlineJudgeResponse();
    JSONToCell jsonToCellConverter = new JSONToCell();
    String fileName = testCase.getId();
    List<String> parameterValues = testCase.getParameterValues();
    JSONObject problemJSONObject = this.filesManager.openProblemJSON(fileName);

    List<Cell> cells = new ArrayList<>();
    JSONArray jsonCellArray = (JSONArray) problemJSONObject.get("cells");
    Iterator jsonCellIterator = jsonCellArray.iterator();
    while (jsonCellIterator.hasNext()) {
      JSONObject jsonCell = (JSONObject) jsonCellIterator.next();
      Cell c = jsonToCellConverter.convert(jsonCell);
      cells.add(c);
    }

    String mainFunctionSignature = (String) problemJSONObject.get("mainFunctionSignature");
    String returnType = (String) problemJSONObject.get("returnType");

    SolutionBuilder solnBuilder = new SolutionBuilder(cells, returnType,
        mainFunctionSignature, parameterValues);
    String solnString = solnBuilder.getSolutionString();
    String resultString = this.filesManager.runProcess(solnString, "Solution");

    onlineJudgeRes.setResultString(resultString);

    return onlineJudgeRes;
  }
}
