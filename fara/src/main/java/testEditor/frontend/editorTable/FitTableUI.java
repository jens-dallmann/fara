package testEditor.frontend.editorTable;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class FitTableUI {

	private JTable table;
	private JScrollPane panel;

	public FitTableUI(TableModel model) {
		table = new JTable(model);
		panel = new JScrollPane(table);
		table.setDefaultRenderer(Object.class, new FitTableCellRenderer());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.getTableHeader().setReorderingAllowed(false);
	}

	public JScrollPane getPanel() {
		return panel;
	}

	public JTable getTable() {
		return table;
	}
}
