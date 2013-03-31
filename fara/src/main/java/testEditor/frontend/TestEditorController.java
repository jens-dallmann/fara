package testEditor.frontend;

import javax.swing.JFrame;

import testEditor.frontend.editorTable.FitHtmlRepresentationController;
import core.exception.frontend.ExceptionHandler;
import core.processableTable.ProcessService;
import fit.Parse;

public class TestEditorController{
	private FitHtmlRepresentationController fitTable;
	private TestEditorUI ui;

	public TestEditorController(ProcessService service, Parse table) {
		this(service, table, "Fara Test Editor");
	}
	public TestEditorController(ProcessService service, Parse table, String title) {
		fitTable = new FitHtmlRepresentationController();
		ui = new TestEditorUI(title);
		defineUncaugtExceptionHandling();
		fitTable.init(service, table);
		ui.addPanel(fitTable.getToolbarUI());
		ui.addTablePanel(fitTable.getTableUI());
	}

	private void defineUncaugtExceptionHandling() {
		Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
	    System.setProperty("sun.awt.exception.handler",
	                       ExceptionHandler.class.getName());
	    ExceptionHandler.setParent(ui.getFrame());
	}
	
	public JFrame getFrame() {
		return ui.getFrame();
	}
}
