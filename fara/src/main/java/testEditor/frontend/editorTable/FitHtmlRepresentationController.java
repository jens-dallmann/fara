package testEditor.frontend.editorTable;

import java.io.File;

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
import core.processableTable.table.ProcessableTableDelegate;
import core.service.FitIOService;
import core.service.ReflectionService;
import core.service.exceptions.FitIOException;
import fit.Parse;
import fit.exception.FitParseException;

public class FitHtmlRepresentationController implements
		PersistenceToolbarDelegate, ProcessableTableDelegate {

	private FitHtmlRepresentationUI ui;
	private FitRowTableModel model;
	private PersistenceToolbarController persistenceToolbar;
	private ProcessableTableComponent<FitRowTableModel> processableTableComponent;
	private FitIOService fitFileService;
	private ReflectionService reflectionService;

	public void init(ProcessService service, Parse table) {
		reflectionService = new ReflectionService();
		model = createTableModel(table);
		fitFileService = new FitIOService();
		persistenceToolbar = new PersistenceToolbarController();
		persistenceToolbar.setPersistenceToolbarDelegate(this);
		
		processableTableComponent = new ProcessableTableComponent<FitRowTableModel>(
				model, service, this);		
		
		String fixtureName = extractFixtureName(table);
		ui = new FitHtmlRepresentationUI(fixtureName);
		ui.addComponent(processableTableComponent.getTable());

		ui.getFixtureComponent().setFixtureChangedDelegate(
				new FixtureChangedDelegate() {
					@Override
					public void fixtureChanged(String text) {
						setNewProcessServiceToTable(text);
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


	private void setNewProcessServiceToTable(String text) {
		ProcessService newProcessService = reflectionService.loadProcessService(text);
		processableTableComponent.setNewProcessService(newProcessService);
	}
	private FitRowTableModel createTableModel(Parse table) {
		if(table == null) {
			try {
				table = new Parse("<table><tr><td></td></tr></table>");
			} catch (FitParseException e) {
				new ExceptionWindowController(null, e, ExceptionLevel.ERROR, new ApplicationExceptionAreaFiller());
			};
		}
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

	public JComponent getTableUI() {
		return ui.getPanel();
	}

	public JComponent getToolbarUI() {
		JPanel panel = new JPanel();
		panel.setLayout(new MigLayout());
		panel.add(processableTableComponent.getToolbar());
		panel.add(persistenceToolbar.getComponent());
		return panel;
	}

	@Override
	public void save() {
		try {
			fitFileService.writeTest(model.getTestFile(), getRows().more, model.getFixtureName());
		} catch (FitIOException e) {
			throwNewBusinessException(e);
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
		Parse table = null;
		try {
			table = fitFileService.readTest(file);
		} catch (FitIOException e) {
			throwNewBusinessException(e);
		}
		if(table != null) {
			model.setTestFile(file);
			model.setNewTable(table);
			String fixtureName = extractFixtureName(table);
			model.setFixtureName(fixtureName);
			ui.fixtureChanged(fixtureName);
		}
	}

	@Override
	public void lastRowProcessed() {
		File file = model.getTestFile();
		if(file == null) {
			file = createTempFile();
		}
		try {
			fitFileService.writeTestResult(file, getRows().more, model.getFixtureName());
		} catch (FitIOException e) {
			throwNewBusinessException(e);
		}
	}

	private void throwNewBusinessException(FitIOException e) {
		new ExceptionWindowController(null, e, ExceptionLevel.ERROR, new ApplicationExceptionAreaFiller());
	}

	private File createTempFile() {
		File file = new File("temp.html");
		model.setTestFile(file);
		save();
		return file;
	}

	public void setTestFile(File file) {
		model.setTestFile(file);
	}
}
