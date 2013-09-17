package frontend.editorTable.tableFunctions;

import core.command.Command;
import core.command.supports.CommandFactory;
import fit.Parse;
import frontend.editorTable.FitRowTableModel;
import frontend.editorTable.tableFunctions.commands.DeleteLineCommand;
import frontend.editorTable.tableFunctions.commands.NewLineCommand;
import frontend.editorTable.tableFunctions.commands.model.DeleteLineCommandModel;
import frontend.editorTable.tableFunctions.commands.model.NewLineCommandModel;

import javax.swing.JTable;
import java.awt.event.KeyEvent;


public class CommandFactoryImpl implements CommandFactory {
  public Command getCommand(KeyEvent event) {
    JTable table = (JTable) event.getSource();
    if (event.getKeyCode() == KeyEvent.VK_R) {
      return createNewLineCommand(table);
    } else if (event.getKeyCode() == KeyEvent.VK_D) {
      return createDeleteLineCommand(table);
    }
    return null;
  }

  private Command createNewLineCommand(JTable table) {
    int firstSelectedRow = table.getSelectedRow();
    int selectedRowCount = table.getSelectedRowCount();
    NewLineCommandModel model = new NewLineCommandModel(firstSelectedRow, selectedRowCount);
    return new NewLineCommand(table, model);
  }

  private Command createDeleteLineCommand(JTable table) {
    int[] selectedRows = table.getSelectedRows();
    Parse[] models = new Parse[selectedRows.length];
    for (int i = 0; i < selectedRows.length; i++) {
      FitRowTableModel tableModel = (FitRowTableModel) table.getModel();
      models[i] = tableModel.getRow(selectedRows[i]);
    }
    DeleteLineCommandModel model = new DeleteLineCommandModel(models, selectedRows);
    return new DeleteLineCommand(table, model);
  }
}
