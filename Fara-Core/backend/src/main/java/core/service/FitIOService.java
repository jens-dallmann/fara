package core.service;

import core.service.exceptions.CreateDirectoryException;
import core.service.exceptions.FitIOException;
import fit.Parse;
import fit.exception.FitParseException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FitIOService {

  private static final String LINE_BREAK = "\n";
  private FileService fileService;
  public static final String DATE_IN_RESULT_FILE = "ddMMyyyy-hhmmss";

  public FitIOService() {
    fileService = new FileService();
  }

  public Parse readTest(File file) throws FitIOException {
    String input = null;
    Parse table = null;
    try {
      input = fileService.readFile(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    if (input != null) {
      table = parse(input);
    }
    return table;
  }

  private Parse parse(String input) throws FitIOException {
    Parse parse;
    try {
      parse = new Parse(input);
    } catch (FitParseException e) {
      throw new FitIOException("Error on parsing input file: " + e.getMessage(), e);
    }
    return parse;
  }

  public void writeTest(File testfile, Parse parse, String fixture) throws FitIOException {
    writeTable(testfile, parse, fixture, false);
  }

  public void writeTestResult(File testfile, Parse parse, String fixture) throws FitIOException, CreateDirectoryException {
    File resultDirectory = createResultDirectory(testfile);
    File resultFile = createResultFile(resultDirectory, testfile.getName());
    writeTable(resultFile, parse, fixture, true);
    copyCss(resultDirectory);
  }

  public File createResultDirectory(File file) throws CreateDirectoryException {
    String filepath = file.getAbsolutePath();
    int lastIndexOf = filepath.lastIndexOf(File.separator);
    String directoryPath = filepath.substring(0, lastIndexOf);
    String resultDirectoryPath = directoryPath + File.separator + "result";
    File directory = new File(resultDirectoryPath);
    if (!directory.exists()) {
      try {
        FileUtils.forceMkdir(directory);
      } catch (IOException e) {
        throw new CreateDirectoryException(resultDirectoryPath);
      }
    }
    return directory;
  }

  private File createResultFile(File resultDirectory, String name) throws FitIOException {
    name = name.replace(".html", "");
    String date = currentTimeAsString();
    name += date;
    name += "_result.html";
    String resultFile = resultDirectory.getAbsolutePath() + File.separator + name;
    try {
      return fileService.createFileIfNotExist(resultFile);
    } catch (Exception e) {
      throw new FitIOException("Error on creating the fit result file", e);
    }
  }

  private String currentTimeAsString() {
    SimpleDateFormat formatter = new SimpleDateFormat(DATE_IN_RESULT_FILE);
    String format = formatter.format(new Date());
    return format;
  }

  private void writeTable(File file, Parse table, String fixture, boolean isResult) throws FitIOException {
    String tableAsHTML = tableAsHTML(table, fixture, isResult);
    try {
      fileService.writeToFileCreateIfNotExist(file.getAbsolutePath(), tableAsHTML);
    } catch (Exception e) {
      throw new FitIOException("Error on writing file: " + file.getAbsolutePath(), e);
    }
  }

  public String tableAsHTML(Parse table, String fixture, boolean result) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"fitnesse.css\">" + LINE_BREAK);
    buffer.append("<table border='1'>" + LINE_BREAK);
    buffer.append("\t<tr>" + LINE_BREAK);
    buffer.append("\t\t<td>" + fixture + "</td></tr>" + LINE_BREAK);
    int rowIndex = 0;
    while (table != null) {
      buffer.append("\t" + table.tag + LINE_BREAK);
      buffer.append(buildResultCells(table.parts, result, rowIndex));
      buffer.append("\t" + table.end);
      buffer.append(LINE_BREAK);
      table = table.more;
      rowIndex++;
    }
    buffer.append("</table>");
    return buffer.toString();
  }

  private String buildResultCells(Parse parts, boolean result, int rowIndex) {
    StringBuffer buffer = new StringBuffer();
    Parse cells = parts;
    while (cells != null) {
      buffer.append("\t\t" + cells.tag);
      if (result) {
        buffer.append(cells.text());
      } else {
        buffer.append(cells.body);
      }
      buffer.append(cells.end + LINE_BREAK);
      cells = cells.more;
    }

    return buffer.toString();
  }

  private void copyCss(File resultDirectory) throws FitIOException {
    try {
      fileService.copyResourceToFile("fitnesse.css", resultDirectory + File.separator + "fitnesse.css");
    } catch (Exception e) {
      throw new FitIOException("Error on writing Fit-Result File: Can not copy stylesheet", e);
    }
  }
}
