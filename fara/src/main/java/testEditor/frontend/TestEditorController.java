package testEditor.frontend;

import testEditor.frontend.editorTable.FitHtmlRepresentationController;
import core.processableTable.ProcessService;
import fit.Parse;

public class TestEditorController{
	private FitHtmlRepresentationController fitTable;
	private TestEditorUI ui;

	public TestEditorController(ProcessService service, Parse table) {
		fitTable = new FitHtmlRepresentationController();
		ui = new TestEditorUI();
		fitTable.init(service, table);
		ui.addPanel(fitTable.getToolbarUI());
		ui.addTablePanel(fitTable.getTableUI());
	}
}
