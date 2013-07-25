package core.processableTable;

import javax.swing.JComponent;
import javax.swing.JTable;

import core.processableTable.table.ProcessableTableController;
import core.processableTable.table.ProcessableTableDelegate;
import core.processableTable.table.model.AbstractProcessableTableModel;
import core.processableTable.toolbar.ProcessToolbarController;
import core.state.StateListener;

public class ProcessableTableComponent<Model extends AbstractProcessableTableModel> implements StateListener<ProcessTableStates>{
	private ProcessableTableController<Model> tableController;
	private ProcessToolbarController toolbarController;
	
	public ProcessableTableComponent(Model model, ProcessService service, ProcessableTableDelegate delegate) {
		toolbarController = new ProcessToolbarController();
		tableController = new ProcessableTableController<Model>(model, service, this, delegate);
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

	public JComponent getTablePanel() {
		return tableController.getComponent();
	}

	public JComponent getToolbar() {
		return toolbarController.getComponent();
	}

	public void setNewProcessService(ProcessService newProcessService) {
		tableController.setNewProcessService(newProcessService);
	}

	public JTable getTable() {
		return tableController.getTable();
	}
}
