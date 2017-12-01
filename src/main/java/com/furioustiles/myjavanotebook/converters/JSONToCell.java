package com.furioustiles.myjavanotebook.converters;

import com.furioustiles.myjavanotebook.util.Cell;
import com.furioustiles.myjavanotebook.util.CellType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter for creating Cell objects out of JSONObjects.
 *
 * @author furioustiles
 */
@Component
public class JSONToCell implements Converter<JSONObject, Cell> {

  private JSONObject jsonObject;

  private String convertId() {
    return (String) this.jsonObject.get("id");
  }

  private CellType convertCellType() {
    String cellTypeAsString = (String) this.jsonObject.get("cellType");
    if (cellTypeAsString.equals("CODE")) {
      return CellType.CODE;
    }
    return CellType.MARKDOWN;
  }

  private String convertCellContent() {
    return (String) this.jsonObject.get("cellContent");
  }

  @Override
  public Cell convert(JSONObject jsonObject) {
    Cell cell = new Cell();
    this.jsonObject = jsonObject;

    cell.setId(convertId());

    cell.setCellType(convertCellType());

    cell.setCellContent(convertCellContent());

    return cell;
  }
}
