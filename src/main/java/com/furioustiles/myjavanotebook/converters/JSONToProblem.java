package com.furioustiles.myjavanotebook.converters;

import com.furioustiles.myjavanotebook.domain.Problem;
import com.furioustiles.myjavanotebook.util.Cell;
import com.furioustiles.myjavanotebook.util.CellType;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter for creating Problem objects out of JSONObjects.
 *
 * @author furioustiles
 */
@Component
public class JSONToProblem implements Converter<JSONObject, Problem> {

  private JSONObject jsonObject;

  private String convertFileName() {
    return (String) this.jsonObject.get("fileName");
  }

  private String convertDisplayName() {
    return (String) this.jsonObject.get("displayName");
  }

  private List<String> convertTags() {
    ArrayList<String> tags = new ArrayList<>();
    JSONArray jsonTagArray = (JSONArray) this.jsonObject.get("tags");
    Iterator jsonTagIterator = jsonTagArray.iterator();

    while (jsonTagIterator.hasNext()) {
      String tag  = (String) jsonTagIterator.next();
      tags.add(tag);
    }

    return tags;
  }

  private Date convertTimeCreated() {
    long timeCreatedValue = (long) this.jsonObject.get("timeCreated");
    Date timeCreated = new Date(timeCreatedValue);

    return timeCreated;
  }

  private Date convertLastModified() {
    long lastModifiedValue = (long) this.jsonObject.get("lastModified");
    Date lastModified = new Date(lastModifiedValue);

    return lastModified;
  }

  private String convertProblemSourceURL() {
    return (String) this.jsonObject.get("problemSourceURL");
  }

  private String convertReturnType() {
    return (String) this.jsonObject.get("returnType");
  }

  private String convertMainFunctionSignature() {
    return (String) this.jsonObject.get("mainFunctionSignature");
  }

  private List<Cell> convertCells() {
    ArrayList<Cell> cellList = new ArrayList<>();
    JSONArray jsonCellArray = (JSONArray) this.jsonObject.get("cells");
    Iterator jsonCellIterator = jsonCellArray.iterator();
    while (jsonCellIterator.hasNext()) {
      JSONObject jsonCell = (JSONObject) jsonCellIterator.next();
      Cell c = new Cell();
      c.setId((String) jsonCell.get("id"));
      c.setCellType(CellType.valueOf((String) jsonCell.get("cellType")));
      c.setCellContent((String) jsonCell.get("cellContent"));
      cellList.add(c);
    }

    return cellList;
  }

  @Override
  public Problem convert(JSONObject jsonObject) {
    Problem problem = new Problem();
    this.jsonObject = jsonObject;

    problem.setFileName(convertFileName());

    problem.setDisplayName(convertDisplayName());

    problem.setTags(convertTags());

    problem.setTimeCreated(convertTimeCreated());

    problem.setLastModified(convertLastModified());

    problem.setProblemSourceURL(convertProblemSourceURL());

    problem.setReturnType(convertReturnType());

    problem.setMainFunctionSignature(convertMainFunctionSignature());

    problem.setCells(convertCells());

    return problem;
  }
}
