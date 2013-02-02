package testEditor.frontend;

import interfaces.DoNextRowObservable;
import interfaces.FaraTestEditor;
import testEditor.frontend.editorTable.FitTableController;
import testEditor.frontend.editorTable.RowState;
import testEditor.frontend.toolbar.ToolbarController;
import testEditor.frontend.toolbar.ToolbarDelegate;
import fit.Parse;

public class TestEditorController implements ToolbarDelegate {
	private FitTableController fitTable;
	private ToolbarController toolbar;
	private TestEditorUI ui;
	private boolean stepForward = false;
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
	private boolean canProcessNext() {
		return shouldStepForward() || shouldPlay();
	}

	public boolean shouldPlay() {
		return play;
	}

	public boolean shouldStepForward() {
		return stepForward;
	}

	public Parse startProcessNextRow() {
		toolbar.setButtonsEnabled(false);
		stepForward = false;
		return fitTable.startRowProcessing();
	}

	public void setResult(String result, String message) {
		RowState rowState = toRowState(result);
		fitTable.actualRowProcessed(rowState, message);
		toolbar.setButtonsEnabled(true);
		if (rowState == RowState.FAILED || fitTable.rowHasBreakpoint()) {
			play = false;
		}
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
		Parse nextRow = startProcessNextRow();
		testEditor.informListenerNextRow(nextRow);
	}

	@Override
	public void play() {
		this.play = true;
	}

	@Override
	public void skip() {
		int selectedRow = fitTable.getSelectedRowIndex();
		fitTable.jumpTo(selectedRow);
	}
}
