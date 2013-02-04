package testEditor.frontend.editorTable;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

public class CellEditor extends DefaultCellEditor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CellEditor() {
		super(new JTextField());
	}
	@Override
	public boolean isCellEditable(EventObject anEvent) {
		System.out.println("Test");
		return super.isCellEditable(anEvent);
	}
	
	@Override
	public boolean stopCellEditing() {
		// TODO Auto-generated method stub
		return super.stopCellEditing();
	}
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		return super.getTableCellEditorComponent(table, value, isSelected, row, column);
	}
}
