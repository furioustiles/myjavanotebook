package com.furioustiles.myjavanotebook.util;

import com.furioustiles.myjavanotebook.util.CellType;

/**
 * A wrapper for holding both the cell type (code or markdown) and its content.
 *
 * @author furioustiles
 */
public class Cell {

  private String id;
  private CellType cellType;
  private String cellContent;

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return this.id;
  }

  public void setCellType(CellType cellType) {
    this.cellType = cellType;
  }

  public CellType getCellType() {
    return this.cellType;
  }

  public void setCellContent(String cellContent) {
    this.cellContent = cellContent;
  }

  public String getCellContent() {
    return this.cellContent;
  }
}
