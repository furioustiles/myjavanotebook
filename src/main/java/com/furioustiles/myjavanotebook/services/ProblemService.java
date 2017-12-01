package com.furioustiles.myjavanotebook.services;

import com.furioustiles.myjavanotebook.commands.UpdateCellsForm;
import com.furioustiles.myjavanotebook.converters.CellToJSON;
import com.furioustiles.myjavanotebook.converters.JSONToCell;
import com.furioustiles.myjavanotebook.converters.JSONToProblem;
import com.furioustiles.myjavanotebook.converters.ProblemToJSON;
import com.furioustiles.myjavanotebook.domain.Problem;
import com.furioustiles.myjavanotebook.util.Cell;
import com.furioustiles.myjavanotebook.util.CellType;
import com.furioustiles.myjavanotebook.util.FilesManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the business logic for delivering, saving, and deleting
 * problems from the filesystem.
 *
 * @author furioustiles
 */
@Service
public class ProblemService {

  private FilesManager filesManager;
  private JSONToCell jsonToCellConverter;
  private CellToJSON cellToJSONConverter;
  private JSONToProblem jsonToProblemConverter;
  private ProblemToJSON problemToJSONConverter;

  @Autowired
  public ProblemService() {
    this.filesManager = new FilesManager();
    this.jsonToCellConverter = new JSONToCell();
    this.cellToJSONConverter = new CellToJSON();
    this.jsonToProblemConverter = new JSONToProblem();
    this.problemToJSONConverter = new ProblemToJSON();
  }

  /**
   * Business logic for opening a problem from the filesystem.
   *
   * @param fileName Name of the file to be opened
   * @return A problem object to be consumed in the REST API
   */
  public Problem openProblem(String fileName) {
    JSONObject problemJSONObject = this.filesManager.openProblemJSON(fileName);
    return this.jsonToProblemConverter.convert(problemJSONObject);
  }

  /**
   * Business logic for opening the newest problem in the filesystem.
   *
   * @return The newest Problem object
   */
  public Problem openNewestProblem() {
    JSONObject newestProblemJSONObject = this.filesManager.openNewestProblemJSON();
    return this.jsonToProblemConverter.convert(newestProblemJSONObject);
  }

  /**
   * Business logic for saving a problem to the filesystem.
   *
   * @param problem Problem object to be saved
   */
  public void saveProblem(Problem problem) {
    String fileName = problem.getFileName();
    JSONObject problemJSONObject = this.problemToJSONConverter.convert(problem);
    this.filesManager.writeProblemJSON(problemJSONObject, fileName);
  }

  /**
   * Business logic for overwriting cells to the filesystem.
   *
   * @param updateCellsForm wrapper containing cells to save
   */
  public void submitCells(UpdateCellsForm updateCellsForm) {
    String fileName = updateCellsForm.getId();
    List<Cell> cellList = updateCellsForm.getCells();
    JSONArray newJSONCellArray = new JSONArray();
    cellList.forEach(c -> newJSONCellArray.add(cellToJSONConverter.convert(c)));
    JSONObject jsonProblemObject = this.filesManager.openProblemJSON(fileName);

    jsonProblemObject.remove("cells");
    jsonProblemObject.put("cells", newJSONCellArray);

    this.filesManager.writeProblemJSON(jsonProblemObject, fileName);
  }

  /**
   * Business logic for inserting a cell into a cell list.
   *
   * @param problemId ID of the problem to add a cell to
   * @param cellType Type of cell to be added
   * @param position Position in the array to add the new cell
   * @return New list of cells with newly inserted cell
   */
  public List<Cell> insertCell(String problemId, String cellType, int position) {
    JSONObject jsonProblemObject = this.filesManager.openProblemJSON(problemId);
    Problem problem = this.jsonToProblemConverter.convert(jsonProblemObject);
    List<Cell> cellList = problem.getCells();

    Cell newCell = new Cell();
    newCell.setId(UUID.randomUUID().toString().replace("-", ""));
    if (cellType.equals("CODE")) {
      newCell.setCellType(CellType.CODE);
    } else {
      newCell.setCellType(CellType.MARKDOWN);
    }
    newCell.setCellContent(new String(""));

    cellList.add(position, newCell);
    problem.setCells(cellList);
    JSONObject newProblemJSON = this.problemToJSONConverter.convert(problem);

    this.filesManager.writeProblemJSON(newProblemJSON, problemId);

    return cellList;
  }

  /**
   * Business logic for deleting a cell from a cell list.
   *
   * @param problemId ID of the problem to delete a cell in
   * @param cellId ID of the cell to delete
   * @return New list of cells without newly deleted cell
   */
  public List<Cell> deleteCell(String problemId, int position) {
    JSONObject jsonProblemObject = this.filesManager.openProblemJSON(problemId);
    Problem problem = this.jsonToProblemConverter.convert(jsonProblemObject);
    List<Cell> cellList = problem.getCells();

    cellList.remove(position);
    problem.setCells(cellList);
    JSONObject newProblemJSON = this.problemToJSONConverter.convert(problem);

    this.filesManager.writeProblemJSON(newProblemJSON, problemId);

    return cellList;
  }

  /**
   * Business logic for deleting a problem from the filesystem.
   *
   * @param fileName Name of the file to be deleted
   */
  public void deleteProblem(String fileName) {
    this.filesManager.removeFile(fileName);
  }
}
