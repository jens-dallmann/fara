package processableTable;

import core.ProcessService;
import processableTable.table.ProcessableTableController;
import processableTable.table.ProcessableTableDelegate;
import processableTable.table.model.AbstractProcessableTableModel;
import processableTable.toolbar.ProcessToolbarController;
import state.StateListener;

import javax.swing.*;

public class ProcessableTableComponent<Model extends AbstractProcessableTableModel> implements StateListener<ProcessTableStates> {
  private ProcessableTableController<Model> tableController;
  private ProcessToolbarController toolbarController;

  public ProcessableTableComponent(Model model, ProcessService service, ProcessableTableDelegate delegate) {
    toolbarController = new ProcessToolbarController();
    tableController = new ProcessableTableController<Model>(model, service, this, delegate);
    toolbarController.setProcessToolbarDelegate(tableController);
  }

  @Override
  public void onStateChange(ProcessTableStates newState) {
    if (newState == ProcessTableStates.EMPTY_TABLE) {
      toolbarController.setButtonsEnabled(false);
    } else if (newState == ProcessTableStates.IDLE) {
      toolbarController.setButtonsEnabled(true);
    } else if (newState == ProcessTableStates.RUNNING) {
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
