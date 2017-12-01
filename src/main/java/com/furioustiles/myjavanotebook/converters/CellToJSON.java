package com.furioustiles.myjavanotebook.converters;

import com.furioustiles.myjavanotebook.util.Cell;
import com.furioustiles.myjavanotebook.util.CellType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter for creating JSONObjects out of Cell objects.
 *
 * @author furioustiles
 */
@Component
public class CellToJSON implements Converter<Cell, JSONObject> {

  private Cell cell;

  private String convertId() {
    return this.cell.getId();
  }

  private String convertCellType() {
    return this.cell.getCellType().toString();
  }

  private String convertCellContent() {
    return this.cell.getCellContent();
  }

  @Override
  public JSONObject convert(Cell cell) {
    JSONObject jsonObject = new JSONObject();
    this.cell = cell;

    jsonObject.put("id", convertId());

    jsonObject.put("cellType", convertCellType());

    jsonObject.put("cellContent", convertCellContent());

    return jsonObject;
  }
}
