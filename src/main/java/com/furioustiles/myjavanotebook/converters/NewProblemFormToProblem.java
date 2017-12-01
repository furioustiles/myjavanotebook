package com.furioustiles.myjavanotebook.converters;

import com.furioustiles.myjavanotebook.commands.NewProblemForm;
import com.furioustiles.myjavanotebook.domain.Problem;
import com.furioustiles.myjavanotebook.util.Cell;
import com.furioustiles.myjavanotebook.util.CellType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converter for converting NewProblemForm objects to Problem objects.
 *
 * @author furioustiles
 */
@Component
public class NewProblemFormToProblem implements Converter<NewProblemForm, Problem> {

  private NewProblemForm newProblemForm;

  private String generateUUID() {
    String uuid = UUID.randomUUID().toString().replace("-","");
    return uuid;
  }

  private String convertFileName() {
    String displayName = this.newProblemForm.getDisplayName();
    displayName = displayName.replace(" ", "_").toLowerCase();

    return displayName + new String("_") + generateUUID() + new String(".mjnb");
  }

  private String convertDisplayName() {
    return this.newProblemForm.getDisplayName();
  }

  private List<String> convertTags() {
    List<String> tagList = Arrays.asList(this.newProblemForm.getTagString().split(","));
    ArrayList<String> tagListTrimmed = new ArrayList<>();
    tagList.forEach(tag -> tagListTrimmed.add(tag.trim()));

    return tagListTrimmed;
  }

  private Date convertTimeCreated() {
    return new Date();
  }

  private Date convertLastModified() {
    return new Date();
  }

  private String convertProblemSourceURL() {
    return this.newProblemForm.getProblemSourceURL();
  }

  private String convertReturnType() {
    return this.newProblemForm.getReturnType();
  }

  private String convertMainFunctionSignature() {
    return this.newProblemForm.getMainFunctionSignature();
  }

  private List<Cell> convertCells() {
    List<Cell> cellList = new ArrayList<>();
    String displayName = this.newProblemForm.getDisplayName();

    Cell markdownCell = new Cell();
    markdownCell.setId(generateUUID());
    markdownCell.setCellType(CellType.MARKDOWN);
    markdownCell.setCellContent(new String("# ") + displayName + new String(" Solution"));

    Cell codeCell = new Cell();
    codeCell.setId(generateUUID());
    codeCell.setCellType(CellType.CODE);
    String codeCellContent = new String("%s %s(/* Enter arguments here */) {\n");
    codeCellContent       += new String("  // Enter your code here\n");
    codeCellContent       += new String("}");
    codeCellContent = String.format(codeCellContent,
        this.newProblemForm.getReturnType(),
        this.newProblemForm.getMainFunctionSignature());
    codeCell.setCellContent(codeCellContent);

    cellList.add(markdownCell);
    cellList.add(codeCell);

    return cellList;
  }

  @Override
  public Problem convert(NewProblemForm newProblemForm) {
    Problem problem = new Problem();
    this.newProblemForm = newProblemForm;

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
