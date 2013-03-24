package testEditor.frontend.editorTable;

import java.io.File;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import testEditor.frontend.persistenceToolbar.PersistenceToolbarController;
import testEditor.frontend.persistenceToolbar.PersistenceToolbarDelegate;
import core.exception.frontend.ApplicationExceptionAreaFiller;
import core.exception.frontend.ExceptionLevel;
import core.exception.frontend.ExceptionWindowController;
import core.processableTable.ProcessService;
import core.processableTable.ProcessableTableComponent;
import core.processableTable.table.ProcessableTableController;
import core.processableTable.table.ProcessableTableDelegate;
import core.processableTable.toolbar.ProcessToolbarController;
import core.service.FileService;
import core.service.exceptions.CreateFileException;
import fit.Parse;
import fit.exception.FitParseException;

public class FitHtmlRepresentationController implements
		PersistenceToolbarDelegate, ProcessableTableDelegate {

	private FitHtmlRepresentationUI ui;
	private FitRowTableModel model;
	private ProcessableTableController processableTable;
	private ProcessToolbarController processToolbar;
	private PersistenceToolbarController persistenceToolbar;
	private FileService fileService;

	public void init(ProcessService service, Parse table) {
		model = createTableModel(table);
		fileService = new FileService();
		ProcessableTableComponent component = new ProcessableTableComponent(
				model, service, this);
		persistenceToolbar = new PersistenceToolbarController();
		persistenceToolbar.setPersistenceToolbarDelegate(this);
		
		processableTable = component.getTable();
		processToolbar = component.getToolbar();
		String fixtureName = extractFixtureName(table);
		ui = new FitHtmlRepresentationUI(fixtureName);
		ui.addComponent(processableTable.getComponent());

		ui.getFixtureComponent().setFixtureChangedDelegate(
				new FixtureChangedDelegate() {
					@Override
					public void fixtureChanged(String text) {
						model.setFixtureName(text);
					}
				});
	}

	private String extractFixtureName(Parse table) {
		if (table == null) {
			return "";
		} else {
			return table.at(0, 0, 0).text();
		}
	}

	private FitRowTableModel createTableModel(Parse table) {
		FitRowTableModel model = new FitRowTableModel();
		model.setTable(table);
		model.initRowStates();
		model.prepareFirstRow();
		model.calculateColumnCount();
		return model;
	}

	private Parse getRows() {
		return model.getTable().parts;
	}

	public String tableAsHTML(boolean result) {
		Parse originalRow = getRows().more;
		StringBuffer buffer = new StringBuffer();
		buffer.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"fitnesse.css\">");
		buffer.append("<table border='1'>");
		buffer.append("<tr><td>"+ui.getFixtureComponent().getText()+"</tr></td>");
		int rowIndex = 0;
		while (originalRow != null) {
			buffer.append(originalRow.tag);
			buffer.append(buildResultCells(originalRow.parts, result, rowIndex));
			buffer.append(originalRow.end);
			originalRow = originalRow.more;
			rowIndex++;
		}
		buffer.append("</table>");
		return buffer.toString();
	}

	private String buildResultCells(Parse parts, boolean result, int rowIndex) {
		StringBuffer buffer = new StringBuffer();
		Parse cells = parts;
		int columnIndex = 2;
		while (cells != null) {
			if(result) {
				buffer.append(cells.tag);
				buffer.append(cells.text());
			}
			else {
				buffer.append("<td>");
				buffer.append(model.getValueAt(rowIndex, columnIndex));
			}
			buffer.append(cells.end);
			cells = cells.more;
			columnIndex++;
		}

		return buffer.toString();
	}


	public JComponent getTableUI() {
		return ui.getPanel();
	}

	public JComponent getToolbarUI() {
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout());
		panel.add(processToolbar.getComponent());
		panel.add(persistenceToolbar.getComponent());
		return panel;
	}

	@Override
	public void save() {
		String htmlTable = tableAsHTML(false);

		try {
			String filepath = model.getTestFile().getAbsolutePath();
			fileService.writeToFileCreateIfNotExist(filepath, htmlTable);
		} catch (Exception e) {
			new ExceptionWindowController(null, e, ExceptionLevel.ERROR, new ApplicationExceptionAreaFiller());
		}
		
	}

	@Override
	public boolean hasFile() {
		return model.hasFile();
	}

	@Override
	public void saveAs(File file) {
		model.setTestFile(file);
		save();
	}
	@Override
	public void load(File file) {
		String input = null;
		try {
			input = fileService.readFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (input != null) {
			Parse parse = parse(input);
			model.setTestFile(file);
			model.setNewTable(parse);
		}
	}

	private Parse parse(String input) {
		Parse parse = null;
		try {
			parse = new Parse(input);
		} catch (FitParseException e) {
			new ExceptionWindowController(null, e, ExceptionLevel.ERROR, new ApplicationExceptionAreaFiller());
		}
		return parse;
	}

	@Override
	public void lastRowProcessed() {
		File file = model.getTestFile();
		if(file == null) {
			file = createTempFile();
		}
		File resultDirectory = createResultDirectory(file);
		File resultFile = createResultFile(resultDirectory, file.getName());
		writeResult(resultFile);
		copyCss(resultDirectory);
	}

	private void copyCss(File resultDirectory) {
		try {
			fileService.copyResourceToFile("fitnesse.css",resultDirectory+File.separator+"fitnesse.css");
		} catch (Exception e) {
			new ExceptionWindowController(null, e, ExceptionLevel.ERROR, new ApplicationExceptionAreaFiller());
		}
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

	private File createResultFile(File resultDirectory, String name) {
		name = name.replace(".html", "");
		name += System.currentTimeMillis();
		name += "_result.html";
		String resultFile = resultDirectory.getAbsolutePath()+File.separator+name;
		try {
			fileService.createFileIfNotExist(resultFile);
		} catch (Exception e) {
			new ExceptionWindowController(null, new CreateFileException(resultFile, e), ExceptionLevel.ERROR, new ApplicationExceptionAreaFiller());
		}
		return new File(resultFile);
	}

	private void writeResult(File resultFile) {
		String tableAsHTML = tableAsHTML(true);
		try {
			fileService.writeToFileCreateIfNotExist(resultFile.getAbsolutePath(), tableAsHTML);
		} catch (Exception e) {
			new ExceptionWindowController(null, e, ExceptionLevel.ERROR, new ApplicationExceptionAreaFiller());
		}
	}

	private File createTempFile() {
		File file = new File("temp.html");
		model.setTestFile(file);
		save();
		return file;
	}
}
