package processableTable.table;

import processableTable.table.model.AbstractProcessableTableModel;
import processableTable.table.model.ProcessableTableCellRenderer;

import javax.swing.*;

public class ProcessableTableUI {

  private JTable table;
  private JScrollPane scrollPanel;

  public ProcessableTableUI(AbstractProcessableTableModel model) {
    table = new JTable(model);
    table.setName("processableTable");
    scrollPanel = new JScrollPane(table);
    table.setDefaultRenderer(Object.class, new ProcessableTableCellRenderer());
    table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    table.getTableHeader().setReorderingAllowed(false);
  }

  public JComponent getComponent() {
    return scrollPanel;
  }

  public JTable getTable() {
    return table;
  }
}
