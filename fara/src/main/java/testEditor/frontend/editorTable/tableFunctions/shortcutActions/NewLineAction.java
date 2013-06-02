package testEditor.frontend.editorTable.tableFunctions.shortcutActions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTable;

import fit.exception.FitParseException;

import testEditor.frontend.editorTable.FitRowTableModel;

public class NewLineAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	private final FitRowTableModel model;

	public NewLineAction(FitRowTableModel model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTable sourceTable = (JTable) e.getSource();
		int firstSelectedRow = sourceTable.getSelectedRow();
		int selectedRowCount = sourceTable.getSelectedRowCount();
		try {
			if(selectedRowCount == 0 && model.getRowCount() == 0) {
				model.addFirstLine();
			}
			else if(selectedRowCount == 0) {
				model.addRowAt(model.getRowCount());
			}
			else {
				model.addLine(firstSelectedRow, selectedRowCount);
			}
		} catch (FitParseException e1) {
			e1.printStackTrace();
		}
	}
}
