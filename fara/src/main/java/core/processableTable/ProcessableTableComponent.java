package core.processableTable;

import core.processableTable.table.ProcessableTableController;
import core.processableTable.table.model.AbstractProcessableTableModel;
import core.processableTable.toolbar.ProcessToolbarController;
import core.state.StateListener;

public class ProcessableTableComponent implements StateListener<ProcessTableStates>{
	private ProcessableTableController tableController;
	private ProcessToolbarController toolbarController;
	
	public ProcessableTableComponent(AbstractProcessableTableModel model, ProcessService service) {
		toolbarController = new ProcessToolbarController();
		tableController = new ProcessableTableController(model, service, this);
		toolbarController.setProcessToolbarDelegate(tableController);
	}

	@Override
	public void onStateChange(ProcessTableStates newState) {
		if(newState == ProcessTableStates.EMPTY_TABLE) {
			toolbarController.setButtonsEnabled(false);
		}
		else if(newState == ProcessTableStates.IDLE) {
			toolbarController.setButtonsEnabled(true);
		}
		else if(newState == ProcessTableStates.RUNNING) {
			toolbarController.setButtonsEnabled(false);
		}
	}

	public ProcessableTableController getTable() {
		return tableController;
	}

	public ProcessToolbarController getToolbar() {
		return toolbarController;
	}
}
