package frontend.editorTable.tableFunctions.commands;

import fit.exception.FitParseException;
import frontend.AbstractTableCommand;
import frontend.editorTable.FitRowTableModel;
import frontend.editorTable.tableFunctions.commands.model.NewLineCommandModel;

import javax.swing.*;


public class NewLineCommand extends AbstractTableCommand<NewLineCommandModel> {

  public NewLineCommand(JTable sourceTable, NewLineCommandModel model) {
    super(sourceTable, model);
  }

  @Override
  public boolean execute() {
    FitRowTableModel model = getTableModel();
    int selectedRow = getModel().getFirstSelectedRow();
    int selectedRowCount = getModel().getSelectedRowCount();
    getModel().setFirstSelectedRow(selectedRow);
    getModel().setSelectedRowCount(selectedRowCount);
    try {
      if (selectedRowCount == 0 && model.getRowCount() == 0) {
        model.addFirstLine();
      } else if (selectedRowCount == 0) {
        model.addRowAt(model.getRowCount());
      } else {
        model.addEmptyRows(selectedRow, selectedRowCount);
      }
    } catch (FitParseException e1) {
      e1.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public boolean undo() {
    int firstSelectedRow = getModel().getFirstSelectedRow();
    int selectedRowCount = getModel().getSelectedRowCount();
    FitRowTableModel tableModel = getTableModel();
    tableModel.deleteRows(firstSelectedRow + selectedRowCount, selectedRowCount);

    return true;
  }

}
