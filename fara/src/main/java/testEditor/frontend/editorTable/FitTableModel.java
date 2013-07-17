package testEditor.frontend.editorTable;

import javax.swing.table.AbstractTableModel;

import fit.Parse;
import fitArchitectureAdapter.HtmlTableUtils;

public class FitTableModel {
	private Parse table;
	private static final int ROWS_FOR_FIXTURE = 1;
	private final AbstractTableModel tableModel;
	private HtmlTableUtils htmlTableUtils;
	
	public FitTableModel(Parse table, AbstractTableModel tableModel) {
		this.table = table;
		this.tableModel = tableModel;
		htmlTableUtils = new HtmlTableUtils();
	}
	
	public void addEmptyRow(int rowInTableComponent) {
		int rowInTable = rowInTableComponent;
		int columnCount = tableModel.getColumnCount();
		Parse newRow = htmlTableUtils.createNewEmptyRow(columnCount);
		
		addRowAtByTableIndex(rowInTable, newRow);
	}

	public void addRowAtByTableIndex(int rowInTable, Parse newRow) {
		Parse selectedRow = table.at(0, rowInTable);
		Parse afterSelectedRow = selectedRow.more;

		selectedRow.more = newRow;
		newRow.more = afterSelectedRow;
	}
	
	public void addEmptyFirstLine() {
		int columnCount = tableModel.getColumnCount();
		table.at(0, 0).more = htmlTableUtils.createNewEmptyRow(columnCount);
	}
	
	public void addEmptyRows(int fromTableModelIndex, int selectedRowCount) {
		for (int i = 0; i < selectedRowCount; i++) {
			addEmptyRow(fromTableModelIndex + selectedRowCount);
		}
	}
	
	public void setNewTable(Parse table) {
		this.table = table;
	}

	public String getFixtureName() {
		if(table == null) {
			return null;
		}
		Parse firstCell = table.at(0, 0, 0);
		if(firstCell == null) {
			return null;
		}
		return firstCell.text();
	}

	public void setFixtureName(String text) {
		boolean hasTable = table != null;
		boolean hasFirstRow = hasTable && table.at(0, 0, 0) != null;
		if (hasTable && hasFirstRow) {
			table.at(0, 0, 0).body = text;
		}
	}
	
	public Parse getRowByTableModelIndex(int index) {
		return table.at(0, index + ROWS_FOR_FIXTURE);
	}
	
	public int getColumnCount() {
		int columnCount = 0;
		if (table != null) {
			Parse rowTemp = table.parts;
			while (rowTemp != null) {
				int rowColumns = countCells(rowTemp);
				columnCount = Math.max(columnCount, rowColumns);
				rowTemp = rowTemp.more;
			}
		}
		return columnCount;
	}

	private int countCells(Parse rowTemp) {
		Parse cells = rowTemp.parts;
		int counter = 0;
		while (cells != null) {
			counter++;
			cells = cells.more;
		}
		return counter;
	}

	public int getRowCountWithoutFixture() {
		int counter = 0;
		if (table != null) {
			Parse rowsTemp = table.at(0,0); //without fixture column
			while (rowsTemp.more != null) {
				counter++;
				rowsTemp = rowsTemp.more;
			}
		}
		return counter;		
	}
	
	public int getRowCountWithFixtureRow() {
		if(table == null) {
			return 0;
		}
		return getRowCountWithoutFixture() + ROWS_FOR_FIXTURE;
	}
	public Parse getRows() {
		return table.at(0,0);		
	}

	public void deleteFirstRow() {
		Parse fixtureRow = table.at(0, 0);
		fixtureRow.more = table.at(0, 2);
	}

	public void deleteLastRow() {
		int lastRow = getRowCountWithFixtureRow()-1;
		Parse nextToLast = table.at(0,lastRow-1);
		nextToLast.more = null;;
	}

	public void deleteRowByTableIndex(int selectedRowInTableModel) {
		int fitRow = selectedRowInTableModel + ROWS_FOR_FIXTURE;
		Parse rowBeforeDeleted = table.at(0,selectedRowInTableModel);
		Parse rowToDelete = table.at(0,fitRow);
		rowBeforeDeleted.more = rowToDelete.more;
	}
	
	protected Parse getRowAt(int tableModelIndex) {
		return table.at(0, tableModelIndex+ROWS_FOR_FIXTURE);
	}
}
