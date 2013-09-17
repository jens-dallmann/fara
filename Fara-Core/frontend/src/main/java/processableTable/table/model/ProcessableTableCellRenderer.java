package processableTable.table.model;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.Color;
import java.awt.Component;


public class ProcessableTableCellRenderer extends DefaultTableCellRenderer {
  private static final long serialVersionUID = 1L;

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected, boolean hasFocus, int row, int column) {
    decorateStateCell(table, row, column);
    decorateBreakpoint(table, row, column);
    return super.getTableCellRendererComponent(table, value, isSelected,
            hasFocus, row, column);
  }

  private void decorateBreakpoint(JTable table, int row, int column) {
    AbstractProcessableTableModel model = extractFitModel(table);
    if (column == model.getBreakpointColumn()) {
      if (model.breakpointAt(row)) {
        setBackground(Color.ORANGE);
      } else {
        setBackground(Color.WHITE);
      }
    }
  }

  private AbstractProcessableTableModel extractFitModel(JTable table) {
    TableModel model = table.getModel();
    if (model instanceof AbstractProcessableTableModel) {
      return (AbstractProcessableTableModel) model;
    }
    return null;
  }

  private void decorateStateCell(JTable table, int row, int column) {
    AbstractProcessableTableModel fitModel = extractFitModel(table);
    if (column == fitModel.getStateColumn()) {
      if (fitModel.rowStateAt(row) == RowState.PROCESSING) {
        setBackground(Color.CYAN);
      } else if (fitModel.rowStateAt(row) == RowState.SUCCESS) {
        setBackground(Color.GREEN);
      } else if (fitModel.rowStateAt(row) == RowState.FAILED) {
        setBackground(Color.RED);
      } else if (fitModel.rowStateAt(row) == RowState.WAIT) {
        setBackground(Color.LIGHT_GRAY);
      } else if (fitModel.rowStateAt(row) == RowState.SKIPPED) {
        setBackground(Color.YELLOW);
      } else {
        setBackground(Color.WHITE);
      }
    } else {
      setBackground(Color.WHITE);
    }
  }

}
