package frontend.editorTable.tableFunctions.commands.model;

import core.command.CommandModel;

public class NewLineCommandModel implements CommandModel{
	private int firstSelectedRow;
	private int selectedRowCount;
	
	public NewLineCommandModel(int firstSelectedRow, int selectedRowCount) {
		this.firstSelectedRow = firstSelectedRow;
		this.selectedRowCount = selectedRowCount;
		
	}
	public int getFirstSelectedRow() {
		return firstSelectedRow;
	}
	public void setFirstSelectedRow(int firstSelectedRow) {
		this.firstSelectedRow = firstSelectedRow;
	}
	public int getSelectedRowCount() {
		return selectedRowCount;
	}
	public void setSelectedRowCount(int selectedRowCount) {
		this.selectedRowCount = selectedRowCount;
	}
}
