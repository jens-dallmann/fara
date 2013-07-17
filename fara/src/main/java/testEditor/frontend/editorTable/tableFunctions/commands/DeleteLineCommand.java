package testEditor.frontend.editorTable.tableFunctions.commands;

import javax.swing.JTable;

import testEditor.frontend.editorTable.tableFunctions.commands.model.DeleteLineCommandModel;
import core.command.AbstractTableCommand;
import fit.Parse;

public class DeleteLineCommand extends
		AbstractTableCommand<DeleteLineCommandModel> {

	public DeleteLineCommand(JTable table, DeleteLineCommandModel model) {
		super(table, model);
	}

	@Override
	public boolean execute() {
		int[] selectedRows = getModel().getSelectedRowsIndizes();
		if(selectedRows.length > 0) {
			getTableModel().deleteRows(selectedRows);
			return true;
		}
		return false;
	}

	@Override
	public boolean undo() {
		int[] selectedRowsIndizes = getModel().getSelectedRowsIndizes();
		Parse[] selectedRows = getModel().getSelectedRows();
		for (int i = 0; i < selectedRowsIndizes.length; i++) {
			getTableModel().addRowAt(selectedRowsIndizes[i], selectedRows[i]);
		}
		return true;
	}

}
