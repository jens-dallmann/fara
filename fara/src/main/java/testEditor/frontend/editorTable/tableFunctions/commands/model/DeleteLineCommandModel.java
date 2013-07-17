package testEditor.frontend.editorTable.tableFunctions.commands.model;

import core.command.CommandModel;
import fit.Parse;

public class DeleteLineCommandModel implements CommandModel {
	private int[] selectedRowsIndizes;
	private Parse[] selectedRows;
	
	public DeleteLineCommandModel(Parse[] selectedRows, int[] selectedRowsIndizes) {
		this.selectedRowsIndizes = selectedRowsIndizes;
		this.selectedRows = selectedRows;
	}

	public int[] getSelectedRowsIndizes() {
		return selectedRowsIndizes;
	}
	
	public Parse[] getSelectedRows() {
		return selectedRows;
	}
}
