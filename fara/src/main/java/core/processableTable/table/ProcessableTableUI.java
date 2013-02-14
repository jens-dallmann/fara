package core.processableTable.table;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import core.processableTable.table.model.AbstractProcessableTableModel;
import core.processableTable.table.model.ProcessableTableCellRenderer;

public class ProcessableTableUI {

	private JTable table;
	private JScrollPane scrollPanel;

	public ProcessableTableUI(AbstractProcessableTableModel model) {
		table = new JTable(model);
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