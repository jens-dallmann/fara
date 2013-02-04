package testEditor.frontend.editorTable.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import testEditor.frontend.editorTable.RowState;
import fit.Parse;

public class FitRowTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<Row> rowsList;
	private int columnCount;
	private int actualRowIndex;

	public static final int NUMBER_CELL = 0;
	public static final int STATE_CELL = 1;
	public static final int COMMAND_CELL = 2;

	public void prepareFirstRow() {
		actualRowIndex = 0;
		Row row = rowsList.get(actualRowIndex);
		row.setState(RowState.WAIT);
	}

	public void calculateColumnCount() {
		for (Row oneRow : rowsList) {
			int rowColumns = oneRow.countCells();
			columnCount = Math.max(columnCount, rowColumns);
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		rowsList.get(rowIndex).setCellText(columnIndex-2, (String)aValue);
		fireTableCellUpdated(rowIndex, columnIndex);
		super.setValueAt(aValue, rowIndex, columnIndex);
	}

	@Override
	public String getColumnName(int column) {
		if (column == NUMBER_CELL) {
			return "No";
		} else if (column == STATE_CELL) {
			return "Command";
		} else if (column == COMMAND_CELL) {
			return "State";
		} else if (getColumnCount() - 1 == column) {
			return "Error Message";
		} else {
			return "Parameter " + (column - 2);
		}
	}

	@Override
	public int getRowCount() {
		return rowsList.size();
	}

	@Override
	public int getColumnCount() {
		return columnCount + 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Row row = getRow(rowIndex);
		if (columnIndex == NUMBER_CELL) {
			if (row.hasBreakpoint()) {
				return rowIndex + "(BP)";
			}
			return rowIndex;
		}
		if (columnIndex == STATE_CELL) {
			return row.getState();
		}
		if (isParameter(columnIndex)) {
			return prepareParameterValue(columnIndex, row);
		}
		if (isErrorCell(columnIndex)) {
			if (row.hasFailed()) {
				return row.getMessage();
			}
		}
		return "";
	}

	private Object prepareParameterValue(int columnIndex, Row row) {
		Parse cell = row.getCell(columnIndex - 2);
		String parameterText = null;
		if (row.hasFailed()) {
			parameterText = extractParameterText(cell.body);
		} else {
			parameterText = cell.text();
		}
		return parameterText;
	}

	private boolean isErrorCell(int columnIndex) {
		return columnIndex == getColumnCount() - 1;
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

	public int getNextRowIndex() {
		return actualRowIndex + 1;
	}

	public void increaseActualRowIndex() {
		actualRowIndex++;
	}
	public void decreaseActualRowIndex() {
		actualRowIndex--;
	}

	public Row getRow(int row) {
		return rowsList.get(row);
	}

	public boolean hasMoreRows() {
		return actualRowIndex + 1 < getRowCount();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public void toggleBreakpoint(int row, int column) {
		if (column == NUMBER_CELL) {
			getRow(row).toggleBreakpoint();
			fireTableRowsUpdated(row, row);
		}
	}

	public boolean actualRowHasBreakpoint() {
		return getActualRow().hasBreakpoint();
	}
	public boolean isBeforeActualRow(int index) {
		return index < actualRowIndex;
	}
	public boolean isAfterActualRow(int index) {
		return index > actualRowIndex;
	}
	public boolean isActualRow(int index) {
		return index == actualRowIndex;
	}

	public void removeBreakpointAtActualRow() {
		getActualRow().removeBreakpoint();
	}

	public void setRows(List<Row> rows) {
		rowsList = rows;
	}

	public Row getActualRow() {
		return getRow(actualRowIndex);
	}

	public void updateActualRow(int cell) {
		fireTableCellUpdated(actualRowIndex, cell);
	}

	public void updateRow(int index) {
		fireTableRowsUpdated(index, index);
	}

	public List<Row> getRows() {
		return rowsList;
	}

	public void updateActualRow() {
		updateRow(actualRowIndex);
	}
	
	public boolean isLastRow() {
		return getRowCount() - 1 == actualRowIndex;
	}
}
