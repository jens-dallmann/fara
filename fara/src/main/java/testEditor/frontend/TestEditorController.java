package testEditor.frontend;

import fit.Parse;
import interfaces.DoNextRowObservable;
import interfaces.FaraTestEditor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import testEditor.frontend.editorTable.FitTableController;
import testEditor.frontend.editorTable.RowState;
import testEditor.frontend.toolbar.ToolbarController;
import testEditor.frontend.toolbar.ToolbarDelegate;

public class TestEditorController implements ToolbarDelegate {
	private FitTableController fitTable;
	private ToolbarController toolbar;
	private TestEditorUI ui;
	private boolean play;
	private final DoNextRowObservable testEditor;

	public TestEditorController(FaraTestEditor testEditor, Parse rows) {
		this.testEditor = testEditor;
		fitTable = new FitTableController(rows);
		toolbar = new ToolbarController(this);
		ui = new TestEditorUI();
		ui.addPanel(toolbar.getPanel());
		ui.addPanel(fitTable.getPanel());
	}

	public Parse startProcessNextRow() {
		toolbar.setButtonsEnabled(false);
		return fitTable.startRowProcessing();
	}

	public void setResult(String result, String message) {
		RowState rowState = toRowState(result);
		fitTable.actualRowProcessed(rowState, message);
		toolbar.setButtonsEnabled(true);
		if (hasMoreRows()) {
			fitTable.enableNextRow();
			if (!canProceed(rowState)) {
				play = false;
			}
			if (play) {
				nextStep();
			}
		}
	}

	private boolean canProceed(RowState rowState) {
		return rowState != RowState.FAILED && !fitTable.rowHasBreakpoint()
				&& (hasMoreRows() || fitTable.isLastRow()) ;
	}

	private RowState toRowState(String result) {
		if ("WRONG".equals(result)) {
			return RowState.FAILED;
		} else if ("RIGHT".equals(result)) {
			return RowState.SUCCESS;
		} else if ("IGNORE".equals(result)) {
			return RowState.IGNORED;
		}
		return null;
	}

	public boolean hasMoreRows() {
		return fitTable.hasMoreRows();
	}

	@Override
	public void nextStep() {
		if (!(fitTable.rowHasBreakpoint() && play)) {
			Parse nextRow = startProcessNextRow();
			testEditor.informListenerNextRow(nextRow);
		} else {
			fitTable.removeRowBreakpoint();
		}
	}

	@Override
	public void play() {
		this.play = true;
		nextStep();
	}

	@Override
	public void skip() {
		fitTable.jumpTo();
		
	}
	
	@Override
	public void save() {
		String htmlTable = fitTable.tableAsHtml();
		
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

}
