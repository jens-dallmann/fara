package frontend.editorTable.tableFunctions.commands;

import fit.Parse;
import frontend.AbstractTableCommand;
import frontend.editorTable.tableFunctions.commands.model.DeleteLineCommandModel;

import javax.swing.JTable;

public class DeleteLineCommand extends
        AbstractTableCommand<DeleteLineCommandModel> {

  public DeleteLineCommand(JTable table, DeleteLineCommandModel model) {
    super(table, model);
  }

  @Override
  public boolean execute() {
    int[] selectedRows = getModel().getSelectedRowsIndizes();
    if (selectedRows.length > 0) {
      getTableModel().deleteRows(selectedRows);
      return true;
    }
    return false;
  }

  @Override
  public boolean undo() {
    int[] selectedRowsIndizes = getModel().getSelectedRowsIndizes();
    Parse[] selectedRows = getModel().getSelectedRows();
    for (int i = 0; i < selectedRowsIndizes.length; i++) {
      getTableModel().addRowAt(selectedRowsIndizes[i], selectedRows[i]);
    }
    return true;
  }

}
