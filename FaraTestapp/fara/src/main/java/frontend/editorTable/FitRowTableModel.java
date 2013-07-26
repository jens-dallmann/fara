package frontend.editorTable;

import java.io.File;

import processableTable.table.model.AbstractProcessableTableModel;
import processableTable.table.model.RowState;
import fit.Parse;
import fit.exception.FitParseException;

public class FitRowTableModel extends AbstractProcessableTableModel {

	private static final long serialVersionUID = 1L;
	private File testFile;
	public static final int COMMAND_CELL = 2;
	private FitTableModel fitTableModel;
	
	public FitRowTableModel(Parse table) {
		fitTableModel = new FitTableModel(table, this);
		setNewTable(table);
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
		return fitTableModel.getRowCountWithoutFixture();
	}

	@Override
	public int getColumnCount() {
		int columnCount = fitTableModel.getColumnCount();
		setFailureMessageColumn(columnCount - 1);
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

	public Parse getRow(int tableRowIndex) {
		return fitTableModel.getRowByTableModelIndex(tableRowIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex < 2) {
			return false;
		}
		return true;
	}

	public void updateRow(int index) {
		fireTableRowsUpdated(index, index);
	}

	public void setFixtureName(String text) {
		fitTableModel.setFixtureName(text);
	}

	public String getFixtureName() {
		return fitTableModel.getFixtureName();
	}

	@Override
	public Object getRowAtPointer() {
		return getRow(getPointer());
	}
	
	public void setNewTable(Parse parse) {
		fitTableModel.setNewTable(parse);
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

	public void addEmptyRows(int from, int selectedRowCount)
			throws FitParseException {
		fitTableModel.addEmptyRows(from, selectedRowCount);
		for(int i = 0; i < selectedRowCount; i++) {
			super.addRowState(from+i);
		}
		fireTableDataChanged();
	}

	public void addRowAt(int index) throws FitParseException {
		fitTableModel.addEmptyRow(index);
		super.addRowState(index);
		fireTableDataChanged();
	}

	public void addFirstLine() throws FitParseException {
		fitTableModel.addEmptyFirstLine();
		super.addRowState(0);
		fireTableDataChanged();
	}

	public void deleteRows(int[] selectedRowsInTableModel) {
		for (int i = selectedRowsInTableModel.length-1; i >= 0; i--) {
			int selectedRowIndexInTableModel = selectedRowsInTableModel[i];
			deleteRow(selectedRowIndexInTableModel);
		}
		fireTableDataChanged();
	}

	public void deleteRow(int selectedRowInTableModel) {
		
		boolean isFirstRow = selectedRowInTableModel == 0;
		boolean isLastRow = !(selectedRowInTableModel < getRowCount());
		
		if (isFirstRow) {
			fitTableModel.deleteFirstRow();
			super.deleteRowState(selectedRowInTableModel);
		}
		else if(isLastRow){
			fitTableModel.deleteLastRow();
			super.deleteRowState(selectedRowInTableModel);
		}
		else {
			fitTableModel.deleteRowByTableIndex(selectedRowInTableModel);
			super.deleteRowState(selectedRowInTableModel);
		}
	}

	public void deleteRows(int firstSelectedRow, int selectedRowCount) {
		int[] rowsToDelete = new int[selectedRowCount];
		for (int i = 0; i < selectedRowCount; i++) {
			rowsToDelete[i] = firstSelectedRow + i;
		}
		deleteRows(rowsToDelete);
	}

	public void addRowAt(int i, Parse parse) {
		fitTableModel.addRowAtByTableIndex(i, parse);
		super.addRowState(i);
		fireTableDataChanged();
	}

	public Parse getRows() {
		return fitTableModel.getRows();
	}
}
