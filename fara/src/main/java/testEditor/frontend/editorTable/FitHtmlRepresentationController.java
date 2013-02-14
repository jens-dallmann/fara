package testEditor.frontend.editorTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import testEditor.frontend.persistenceToolbar.PersistenceToolbarController;
import testEditor.frontend.persistenceToolbar.PersistenceToolbarDelegate;
import core.processableTable.ProcessService;
import core.processableTable.ProcessableTableComponent;
import core.processableTable.table.ProcessableTableController;
import core.processableTable.toolbar.ProcessToolbarController;
import fit.Parse;
import fit.exception.FitParseException;

public class FitHtmlRepresentationController implements
		PersistenceToolbarDelegate {

	private FitHtmlRepresentationUI ui;
	private FitRowTableModel model;
	private ProcessableTableController processableTable;
	private ProcessToolbarController processToolbar;
	private PersistenceToolbarController persistenceToolbar;

	public void init(ProcessService service, Parse table) {
		model = createTableModel(table);
		ProcessableTableComponent component = new ProcessableTableComponent(
				model, service);
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

	public String tableAsHtml() {
		Parse originalRow = getRows();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<table border='1'>");
		while (originalRow != null) {
			buffer.append(originalRow.tag);
			buffer.append(buildCells(originalRow.parts));
			buffer.append(originalRow.end);
			originalRow = originalRow.more;
		}
		buffer.append("</table>");
		return buffer.toString();
	}

	private String buildCells(Parse parts) {
		StringBuffer buffer = new StringBuffer();
		Parse cells = parts;
		while (cells != null) {

			buffer.append(cells.tag);
			buffer.append(cells.text());
			buffer.append(cells.end);
			cells = cells.more;
		}

		return buffer.toString();
	}

	@Override
	public void save() {
		String htmlTable = tableAsHtml();

		File test = new File("C:\\data\\fara\\trunk\\fara\\test.html");
		try {
			test.createNewFile();
			FileWriter fileWriter = new FileWriter(test);
			fileWriter.write(htmlTable);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	public void load(File file) {
		String input = readFile(file);
		if (input != null) {
			Parse parse = parse(input);
			model.setNewTable(parse);
		}
	}

	private Parse parse(String input) {
		Parse parse = null;
		try {
			parse = new Parse(input);
		} catch (FitParseException e) {
			e.printStackTrace();
		}
		return parse;
	}

	private String readFile(File file) {
		String content = "";
		try {
			content = new Scanner(file).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return content;
	}
}
