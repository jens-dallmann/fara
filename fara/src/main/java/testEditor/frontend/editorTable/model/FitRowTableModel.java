package testEditor.frontend.editorTable.model;

import java.util.ArrayList;
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

	public FitRowTableModel(Parse rows) {
		createRowList(rows);
		calculateColumnCount();
		prepareFirstRow();
	}

	private void prepareFirstRow() {
		actualRowIndex = 0;
		Row row = rowsList.get(actualRowIndex);
		row.setState(RowState.WAIT);
	}

	private void createRowList(Parse rows) {
		rowsList = new ArrayList<Row>();
		while (rows != null) {
			rowsList.add(new Row(rows));
			rows = rows.more;
		}
	}

	private void calculateColumnCount() {
		for (Row oneRow : rowsList) {
			int rowColumns = oneRow.countCells();
			columnCount = Math.max(columnCount, rowColumns);
		}
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
		if (columnIndex == NUMBER_CELL) {
			Row row = getRow(rowIndex);
			if (row.hasBreakpoint()) {
				return rowIndex + "" + "*";
			}
			return rowIndex;
		}
		Row row = rowsList.get(rowIndex);
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

	public Parse startProcessActualRow() {
		Row row = rowsList.get(actualRowIndex);
		row.setProcessing();
		fireTableCellUpdated(actualRowIndex, STATE_CELL);
		return row.getOriginalRow();
	}

	public void actualRowProcessed(RowState state, String message) {
		handleProcessedActualRow(state, message);
		enableNextRow();
	}

	private void handleProcessedActualRow(RowState state, String message) {
		Row row = rowsList.get(actualRowIndex);
		row.setState(state);
		if (state == RowState.FAILED) {
			row.setMessage(message);
		}
		fireTableRowsUpdated(actualRowIndex, actualRowIndex);
	}

	private void enableNextRow() {
		actualRowIndex++;
		if (actualRowIndex < rowsList.size()) {
			Row row = rowsList.get(actualRowIndex);
			row.setState(RowState.WAIT);
			fireTableRowsUpdated(actualRowIndex, actualRowIndex);
		}
	}

	public Row getRow(int row) {
		return rowsList.get(row);
	}

	public boolean hasMoreRows() {
		return actualRowIndex < getRowCount();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return true;
		}
		return false;
	}

	public void toggleBreakpoint(int row, int column) {
		if (column == NUMBER_CELL) {
			getRow(row).toggleBreakpoint();
			fireTableRowsUpdated(row, row);
		}
	}

	public boolean rowHasBreakpoint() {
		if (hasMoreRows()) {
			Row row = rowsList.get(actualRowIndex);
			return row.hasBreakpoint();
		} else {
			return false;
		}
	}

	public void jumpTo(int selectedRow) {
		while(actualRowIndex != selectedRow) {
			getRow(actualRowIndex).setState(RowState.SKIPPED);
			if(selectedRow > actualRowIndex) {
				actualRowIndex ++;
			}
			else if(selectedRow < actualRowIndex) {
				actualRowIndex --;
			}
		}
		getRow(actualRowIndex).setState(RowState.WAIT);
		fireTableRowsUpdated(0, getRowCount());
	}
}
