package com.furioustiles.myjavanotebook.controllers;

import com.furioustiles.myjavanotebook.commands.NewProblemForm;
import com.furioustiles.myjavanotebook.commands.UpdateCellsForm;
import com.furioustiles.myjavanotebook.converters.NewProblemFormToProblem;
import com.furioustiles.myjavanotebook.domain.Problem;
import com.furioustiles.myjavanotebook.services.ProblemService;
import com.furioustiles.myjavanotebook.util.Cell;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller responsible for opening, saving, and deleting problems from the filesystem.
 *
 * @author furioustiles
 */
@RestController
public class ProblemController {

  private ProblemService problemService;

  @Autowired
  public void setProblemService(ProblemService problemService) {
    this.problemService = problemService;
  }

  /**
   * Handles HTTP GET requests for opening a problem object given the name of the file.
   *
   * @param fileName Name of the file to be opened
   * @return Problem object with given file name
   */
  @RequestMapping(value = "/api/problem", method = RequestMethod.GET)
  public Problem getProblem(@RequestParam("id") String fileName) {
    return this.problemService.openProblem(fileName);
  }

  /**
   * Handles HTTP GET requests for opening the newest problem object.
   *
   * @return The newest Problem object
   */
  @RequestMapping(value = "/api/problem/newest", method = RequestMethod.GET)
  public Problem getNewestProblem() {
    return this.problemService.openNewestProblem();
  }

  /**
   * Handles HTTP POST requests for creating new problems and saving them to the filesystem.
   *
   * @param problemCreateForm Form to be converted and saved to the filesystem
   */
  @RequestMapping(value = "/api/problem/new", method = RequestMethod.POST)
  public void createProblem(@RequestBody NewProblemForm problemForm) {
    NewProblemFormToProblem converter = new NewProblemFormToProblem();
    Problem problem = converter.convert(problemForm);
    this.problemService.saveProblem(problem);
  }

  /**
   * Handles HTTP POST requests for saving previously created problems and overwriting them to the
   * filesystem.
   *
   * @param solutionForm Form containing source code to overwrite in the filesystem
   */
  @RequestMapping(value = "/api/problem/edit", method = RequestMethod.POST)
  public void editProblem(@RequestBody UpdateCellsForm updateCellsForm) {
    this.problemService.submitCells(updateCellsForm);
  }

  /**
   * Handles HTTP POST requests for adding a cell into the cell list for a given problem.
   *
   * @param problemId ID of the problem to add a cell to
   * @param cellType Type of cell to be added
   * @param position Position in the array to add the new cell
   * @return New list of cells with newly inserted cell
   */
  @RequestMapping(value = "/api/problem/edit/insert", method = RequestMethod.POST)
  public List<Cell> insertCell(@RequestParam("id") String problemId,
      @RequestParam("cellType") String cellType, @RequestParam("position") int position) {
    return this.problemService.insertCell(problemId, cellType, position);
  }

  /**
   * Handles HTTP DELETE requests for deleting a cell in the cell list for a given problem.
   *
   * @param problemId ID of the problem to delete a cell in
   * @param position Position in the array to delete the cell
   * @return New list of cells without newly deleted cell
   */
  @RequestMapping(value = "/api/problem/edit/delete", method = RequestMethod.DELETE)
  public List<Cell> deleteCell(@RequestParam("id") String problemId,
      @RequestParam("position") int position) {
    return this.problemService.deleteCell(problemId, position);
  }

  /**
   * Handles HTTP DELETE requests for deleting problems from the filesystem given the name of the
   * file.
   *
   * @param fileName Name of the file to be deleted
   */
   @RequestMapping(value = "/api/problem/delete", method = RequestMethod.DELETE)
  public void deleteProblem(@RequestParam("id") String fileName) {
    this.problemService.deleteProblem(fileName);
  }
}
