package testEditor.frontend.editorTable;

import java.io.File;

import core.processableTable.table.model.AbstractProcessableTableModel;
import core.processableTable.table.model.RowState;
import fit.Parse;
import fit.exception.FitParseException;

public class FitRowTableModel extends AbstractProcessableTableModel {

	private static final long serialVersionUID = 1L;
	private Parse table;
	private int columnCount;
	private File testFile;

	public static final int COMMAND_CELL = 2;

	public void calculateColumnCount() {
		if (table != null) {
			Parse rowTemp = table.parts;
			while (rowTemp != null) {
				int rowColumns = countCells(rowTemp);
				columnCount = Math.max(columnCount, rowColumns);
				rowTemp = rowTemp.more;
			}
		}
		setFailureMessageColumn(getColumnCount() - 1);
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

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if (columnIndex > 1 && columnIndex < getColumnCount() - 1) {
			getCell(rowIndex, columnIndex).body = (String) aValue;
			updateRow(rowIndex);
			super.setValueAt(aValue, rowIndex, columnIndex);
		}
	}

	@Override
	public String getColumnName(int column) {
		if (column == getBreakpointColumn()) {
			return "No";
		} else if (column == getStateColumn()) {
			return "State";
		} else if (column == COMMAND_CELL) {
			return "Command";
		} else if (getColumnCount() - 1 == column) {
			return "Error Message";
		} else {
			return "Parameter " + (column - 2);
		}
	}

	@Override
	public int getRowCount() {
		int counter = 0;
		if (table != null) {
			Parse rowsTemp = table.parts;
			while (rowsTemp != null) {
				counter++;
				rowsTemp = rowsTemp.more;
			}
			counter -= 1;
		}
		return counter; // -1 because the first one is the fixture row and is
						// not listed in the table
	}

	@Override
	public int getColumnCount() {
		return columnCount + super.getColumnCount();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (isParameter(columnIndex)) {
			return prepareParameterValue(columnIndex, rowIndex);
		} else {
			return super.getValueAt(rowIndex, columnIndex);
		}
	}

	private Object prepareParameterValue(int columnIndex, int rowIndex) {
		Parse cell = getCell(rowIndex, columnIndex);
		String parameterText = null;
		if (rowStateAt(rowIndex) == RowState.FAILED) {
			parameterText = extractParameterText(cell.body);
		} else {
			parameterText = cell.text();
		}
		return parameterText;
	}

	private Parse getCell(int rowIndex, int columnIndex) {
		return getRow(rowIndex).at(0, columnIndex - 2);
	}

	private String extractParameterText(String text) {
		int end = text.indexOf("<span");
		if (end != -1) {
			String parameterText = text.substring(0, end);
			return parameterText.trim();
		} else {
			return text;
		}
	}

	private boolean isParameter(int columnIndex) {
		return columnIndex > 1 && !isErrorCell(columnIndex);
	}

	public Parse getRow(int row) {
		return table.at(0, row + 1); // the first row is the fixture name not a
										// command
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex < 2) {
			return false;
		}
		return true;
	}

	public void setTable(Parse table) {
		this.table = table;
		fireTableDataChanged();
	}

	public void updateRow(int index) {
		fireTableRowsUpdated(index, index);
	}

	public Parse getTable() {
		return table;
	}

	public void setFixtureName(String text) {
		if (table != null && table.at(0, 0, 0) != null) {
			table.at(0, 0, 0).body = text;
		}
	}

	public String getFixtureName() {
		return table.at(0, 0, 0).text();
	}

	@Override
	public Object getRowAtPointer() {
		return getRow(getPointer());
	}

	public void setNewTable(Parse parse) {
		this.table = parse;
		calculateColumnCount();
		initRowStates();
		resetProcessableCounter();
		fireTableStructureChanged();
		fireTableDataChanged();
	}

	@Override
	public void publishResult(RowState state, String message) {
		super.publishResult(state, message);
	}

	public File getTestFile() {
		return testFile;
	}

	public void setTestFile(File file) {
		this.testFile = file;
	}

	public boolean hasFile() {
		return testFile != null;
	}

	public void addLine(int firstSelectedRow, int selectedRowCount)
			throws FitParseException {
		for (int i = 0; i < selectedRowCount; i++) {
			addRowAt(firstSelectedRow+selectedRowCount);
		}
	}
	public void addRowAt(int index) throws FitParseException{
		Parse lastSelectedRow = table.at(0, index);
		Parse newRow = createNewEmptyRow();
		Parse afterLastSelectedRow = lastSelectedRow.more;
		newRow.more = afterLastSelectedRow;
		lastSelectedRow.more = newRow;
		super.addRowState(index);
		fireTableDataChanged();
	}
	public void addFirstLine() throws FitParseException {
		table.at(0,0).more = createNewEmptyRow();
		super.addRowState(0);
		fireTableDataChanged();
	}
	private Parse createNewEmptyRow() throws FitParseException {
		StringBuffer emptyTable = new StringBuffer();
		emptyTable.append("<tr>");
		for (int i = 0; i < columnCount; i++) {
			emptyTable.append("<td> </td>");
		}
		emptyTable.append("</tr>");
		Parse parse = new Parse(emptyTable.toString(), new String[] { "tr",
				"td" });
		return parse;
	}

	public void deleteLine(int[] selectedRows) {
		for (int i = selectedRows.length - 1; i >= 0; i--) {
			if (selectedRows[i] - 1 < 0) {
				table.at(0).parts = table.at(0, selectedRows[i] + 1);
			} else {
				Parse previousRow = table.at(0, selectedRows[i]);
				if (selectedRows[i] < getRowCount()) {
					Parse nextRow = table.at(0, selectedRows[i]+1).more;
					previousRow.more = nextRow;
				} else {
					previousRow.more = null;
				}
				super.deleteRowState(selectedRows[i]);
			}
		}
		fireTableDataChanged();
	}
}
