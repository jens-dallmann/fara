package testEditor.frontend.editorTable.tableFunctions.shortcutActions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTable;

import testEditor.frontend.editorTable.FitRowTableModel;

public class DeleteLinesAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	private final FitRowTableModel model;

	public DeleteLinesAction(FitRowTableModel model) {
		this.model = model;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JTable sourceTable = (JTable) e.getSource();
		int[] selectedRows = sourceTable.getSelectedRows();
		model.deleteLine(selectedRows);
	}

}
