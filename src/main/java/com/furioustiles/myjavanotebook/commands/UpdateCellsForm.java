package com.furioustiles.myjavanotebook.commands;

import com.furioustiles.myjavanotebook.util.Cell;
import java.util.List;

/**
 * Wrapper class for submitting solutions as an HTTP POST request.
 *
 * @author furioustiles
 */
public class UpdateCellsForm {

  private String id;
  private List<Cell> cells;

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return this.id;
  }

  public void setCells(List<Cell> cells) {
    this.cells = cells;
  }

  public List<Cell> getCells() {
    return this.cells;
  }
}
