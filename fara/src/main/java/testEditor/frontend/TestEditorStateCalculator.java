package testEditor.frontend;

import testEditor.frontend.editorTable.RowState;
import testEditor.frontend.editorTable.model.FitRowTableModel;
import core.state.AbstractStateCalculator;

public class TestEditorStateCalculator extends AbstractStateCalculator<TestEditorState> {

	private final FitRowTableModel model;
	private TestEditorState state;

	public TestEditorStateCalculator(FitRowTableModel model) {
		this.model = model;
	}
	
	@Override
	public void calculateState() {
		TestEditorState tempState;
		if(isEmptyTableState()) {
			tempState = TestEditorState.EMPTY_TABLE;
		}
		else if(isRunningState()) {
			tempState = TestEditorState.RUNNING;
		}
		else {
			tempState = TestEditorState.IDLE;
		}
		if(!isOldState(tempState)) {
			state = tempState;
			this.fireStateChanged(state); 
		}
	}
	
	private boolean isOldState(TestEditorState tempState) {
		return state == tempState;
	}

	private boolean isRunningState() {
		return model.getPointerState() == RowState.PROCESSING;
	}

	private boolean isEmptyTableState() {
		return model.getRowCount() == 0;
	}
	
	public TestEditorState getState() {
		return state;
	}
	
}