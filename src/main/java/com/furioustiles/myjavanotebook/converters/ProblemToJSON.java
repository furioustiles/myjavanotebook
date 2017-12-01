package com.furioustiles.myjavanotebook.converters;

import com.furioustiles.myjavanotebook.domain.Problem;
import com.furioustiles.myjavanotebook.util.Cell;
import com.furioustiles.myjavanotebook.util.CellType;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter for converting Problem objects to JSONObjects.
 *
 * @author furioustiles
 */
@Component
public class ProblemToJSON implements Converter<Problem, JSONObject> {

  private Problem problem;

  private String convertFileName() {
    return this.problem.getFileName();
  }

  private String convertDisplayName() {
    return this.problem.getDisplayName();
  }

  private JSONArray convertTags() {
    List<String> tags = this.problem.getTags();
    JSONArray jsonTagArray = new JSONArray();
    tags.forEach(tag -> jsonTagArray.add(tag));

    return jsonTagArray;
  }

  private long convertTimeCreated() {
    return this.problem.getTimeCreated().getTime();
  }

  private long convertLastModified() {
    return this.problem.getLastModified().getTime();
  }

  private String convertProblemSourceURL() {
    return this.problem.getProblemSourceURL();
  }

  private String convertReturnType() {
    return this.problem.getReturnType();
  }

  private String convertMainFunctionSignature() {
    return this.problem.getMainFunctionSignature();
  }

  private JSONArray convertCells() {
    List<Cell> cells = this.problem.getCells();
    JSONArray jsonCellArray = new JSONArray();

    for (Cell c : cells) {
      JSONObject jsonCell = new JSONObject();
      jsonCell.put("id", c.getId());
      jsonCell.put("cellType", c.getCellType().toString());
      jsonCell.put("cellContent", c.getCellContent());
      jsonCellArray.add(jsonCell);
    }

    return jsonCellArray;
  }

  @Override
  public JSONObject convert(Problem problem) {
    JSONObject jsonObject = new JSONObject();
    this.problem = problem;

    jsonObject.put("fileName", convertFileName());

    jsonObject.put("displayName", convertDisplayName());

    jsonObject.put("tags", convertTags());

    jsonObject.put("timeCreated", convertTimeCreated());

    jsonObject.put("lastModified", convertLastModified());

    jsonObject.put("problemSourceURL", convertProblemSourceURL());

    jsonObject.put("returnType", convertReturnType());

    jsonObject.put("mainFunctionSignature", convertMainFunctionSignature());

    jsonObject.put("cells", convertCells());

    return jsonObject;
  }
}
