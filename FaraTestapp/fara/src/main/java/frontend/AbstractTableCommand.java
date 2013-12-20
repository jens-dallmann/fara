package frontend;

import core.command.CommandModel;
import core.command.TableCommand;
import frontend.editorTable.FitRowTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;

public abstract class AbstractTableCommand<Model extends CommandModel> implements TableCommand {
  private JTable table;
  private Model model;

  public AbstractTableCommand(JTable table, Model model) {
    this.table = table;
    this.model = model;
  }

  public void setTable(JTable table) {
    this.table = table;
  }

  public JTable getTable() {
    return table;
  }

  public FitRowTableModel getTableModel() {
    TableModel model = table.getModel();

    if (model instanceof FitRowTableModel) {
      return (FitRowTableModel) model;
    }
    return null;
  }

  public Model getModel() {
    return model;
  }

  public void setModel(Model model) {
    this.model = model;
  }
}
