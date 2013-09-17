package fitArchitectureAdapter;

import fit.Parse;
import fit.exception.FitParseException;

import java.util.ArrayList;
import java.util.List;

public class HtmlTableUtils {

  public Parse createNewEmptyRow(int columnCount) {
    List<String> columnContent = new ArrayList<String>();
    for (int i = 0; i < columnCount; i++) {
      columnContent.add(" ");
    }
    return createFilledRow(columnContent);
  }

  public Parse createTableWithEmptyFixture(int columnCount) {
    try {
      Parse tableWithFixture = new Parse("<table></table>", new String[]{"table"});
      tableWithFixture.parts = createNewEmptyRow(columnCount);
      return tableWithFixture;
    } catch (FitParseException e1) {
      e1.printStackTrace();
    }
    return null;
  }

  public Parse createFilledRow(List<String> columns) {
    StringBuffer filledRow = new StringBuffer();
    filledRow.append("<tr>");
    for (String oneColumnContent : columns) {
      filledRow.append("<td>" + oneColumnContent + "</td>");
    }
    filledRow.append("</tr>");


    try {
      return new Parse(filledRow.toString(), new String[]{"tr", "td"});
    } catch (FitParseException e) {
      e.printStackTrace();
    }
    return null;
  }
}