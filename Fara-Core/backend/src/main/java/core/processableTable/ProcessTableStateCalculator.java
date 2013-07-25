package core.processableTable;

import core.processableTable.table.model.AbstractProcessableTableModel;
import core.processableTable.table.model.RowState;
import core.state.AbstractStateCalculator;

public class ProcessTableStateCalculator extends AbstractStateCalculator<ProcessTableStates> {

	private final AbstractProcessableTableModel model;
	private ProcessTableStates state;

	public ProcessTableStateCalculator(AbstractProcessableTableModel model) {
		this.model = model;
	}
	
	@Override
	public void calculateState() {
		ProcessTableStates tempState;
		if(isEmptyTableState()) {
			tempState = ProcessTableStates.EMPTY_TABLE;
		}
		else if(isRunningState()) {
			tempState = ProcessTableStates.RUNNING;
		}
		else {
			tempState = ProcessTableStates.IDLE;
		}
		if(!isOldState(tempState)) {
			state = tempState;
			this.fireStateChanged(state); 
		}
	}
	
	private boolean isOldState(ProcessTableStates tempState) {
		return state == tempState;
	}

	private boolean isRunningState() {
		return model.getPointerState() == RowState.PROCESSING;
	}

	private boolean isEmptyTableState() {
		return model.getRowCount() == 0;
	}
	
	public ProcessTableStates getState() {
		return state;
	}
	
}