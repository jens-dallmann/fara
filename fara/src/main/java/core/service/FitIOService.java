package core.service;

import java.io.File;
import java.io.FileNotFoundException;

import core.service.exceptions.FitIOException;
import fit.Parse;
import fit.exception.FitParseException;

public class FitIOService {
	
	private FileService fileService;
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
		if(input != null) {
			table = parse(input);
		}
		return table;
	}

	private Parse parse(String input) throws FitIOException {
		Parse parse = null;
		try {
			parse = new Parse(input);
		} catch (FitParseException e) {
			throw new FitIOException("Error on parsing input file: "+e.getMessage(), e);
		}
		return parse;
	}
	public void writeTest(File testfile, Parse parse, String fixture) throws FitIOException {
		writeTable(testfile, parse, fixture, false);
	}
	
	public void writeTestResult(File testfile, Parse parse, String fixture) throws FitIOException{
		File resultDirectory = createResultDirectory(testfile);
		File resultFile = createResultFile(resultDirectory, testfile.getName());
		writeTable(resultFile, parse, fixture, true);
		copyCss(resultDirectory);
	}

	private File createResultDirectory(File file) {
		String filepath = file.getAbsolutePath();
		int lastIndexOf = filepath.lastIndexOf(File.separator);
		String directoryPath = filepath.substring(0,lastIndexOf);
		File directory = new File(directoryPath+File.separator+"result");
		if(!directory.exists()) {
			directory.mkdir();
		}
		return directory;
	}

	private File createResultFile(File resultDirectory, String name) throws FitIOException {
		name = name.replace(".html", "");
		name += System.currentTimeMillis();
		name += "_result.html";
		String resultFile = resultDirectory.getAbsolutePath()+File.separator+name;
		try {
			fileService.createFileIfNotExist(resultFile);
		} catch (Exception e) {
			throw new FitIOException("Error on creating the fit result file", e);
		}
		return new File(resultFile);
	}

	private void writeTable(File file, Parse table, String fixture, boolean isResult) throws FitIOException {
		String tableAsHTML = tableAsHTML(table, fixture, isResult);
		try {
			fileService.writeToFileCreateIfNotExist(file.getAbsolutePath(), tableAsHTML);
		} catch (Exception e) {
			throw new FitIOException("Error on writing file: "+file.getAbsolutePath(), e);
		}
	}
	
	public String tableAsHTML(Parse table, String fixture, boolean result) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"fitnesse.css\">");
		buffer.append("<table border='1'>");
		buffer.append("<tr><td>"+fixture+"</tr></td>");
		int rowIndex = 0;
		while (table != null) {
			buffer.append(table.tag);
			buffer.append(buildResultCells(table.parts, result, rowIndex));
			buffer.append(table.end);
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
			buffer.append(cells.tag);
			if(result) {
				buffer.append(cells.text());
			}
			else {
				buffer.append(cells.body);
			}
			buffer.append(cells.end);
			cells = cells.more;
		}

		return buffer.toString();
	}
	private void copyCss(File resultDirectory) throws FitIOException {
		try {
			fileService.copyResourceToFile("fitnesse.css",resultDirectory+File.separator+"fitnesse.css");
		} catch (Exception e) {
			throw new FitIOException("Error on writing Fit-Result File: Can not copy stylesheet", e);
		}
	}
}
