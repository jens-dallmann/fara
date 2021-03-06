/*******************************************************************************
 * Copyright (c) 2013 Jens Dallmann.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jens Dallmann - initial API and implementation
 ******************************************************************************/
package documentor;

import java.util.List;

public class HTMLBuilder {

  public String build(List<FitCommandDoc> process) {
    int highestCountOfParams = locateHighestCountOfParams(process);
    StringBuffer buffer = new StringBuffer();
    startHtmlPage(buffer);
    createHead(buffer);
    startBody(buffer);
    startTable(buffer);
    tableHeaderLine(highestCountOfParams, buffer);
    addCommands(buffer, process, highestCountOfParams);
    endTable(buffer);
    endBody(buffer);
    endHtml(buffer);
    return buffer.toString();
  }

  private void endHtml(StringBuffer buffer) {
    buffer.append("</html>");
  }

  private void endBody(StringBuffer buffer) {
    buffer.append("</body>");
  }

  private void endTable(StringBuffer buffer) {
    buffer.append("</table>");
  }

  private void addCommands(StringBuffer buffer, List<FitCommandDoc> process, int highestCountOfParams) {
    for (FitCommandDoc oneDoc : process) {
      addCommand(buffer, oneDoc, highestCountOfParams);
    }
  }

  private void startTable(StringBuffer buffer) {
    buffer.append("<table border='1'>");
  }

  private void tableHeaderLine(int highestCountOfParams, StringBuffer buffer) {
    buffer.append("<tr>");
    buffer.append("<td> <b>ClassName.CommandName</b></td>");
    for (int i = 0; i < highestCountOfParams; i++) {
      buffer.append("<td><b> Parameter ").append((i + 1)).append("</b></td>");
    }
    buffer.append("</tr>");
  }

  private void addCommand(StringBuffer buffer, FitCommandDoc oneDoc, int highestCountOfParams) {
    buffer.append("<tr>");
    buffer.append("<td>" + oneDoc.getClassName() + "." + oneDoc.getCommandName() + "</td>");
    List<String> commandParams = oneDoc.getCommandParams();
    for (String oneParam : commandParams) {
      buffer.append("<td>" + oneParam + "</td>");
      highestCountOfParams--;
    }
    for (int i = 0; i < highestCountOfParams; i++) {
      buffer.append("<td>&nbsp </td>");
    }
    buffer.append("</tr>");
  }

  private int locateHighestCountOfParams(List<FitCommandDoc> process) {
    int highestCountOfParams = 0;
    for (FitCommandDoc doc : process) {
      int paramSize = doc.getCommandParams().size();
      if (paramSize > highestCountOfParams) {
        highestCountOfParams = paramSize;
      }
    }
    return highestCountOfParams;
  }

  private void startBody(StringBuffer buffer) {
    buffer.append("<body>");
  }

  private void createHead(StringBuffer buffer) {
    buffer.append("<head>");
    buffer.append("<title> Fit Commands </title>");
    buffer.append("</head>");
  }

  private void startHtmlPage(StringBuffer buffer) {
    buffer.append("<html>");
  }

}
