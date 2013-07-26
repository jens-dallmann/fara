package processableTable.table.model;

import org.apache.commons.collections.CollectionUtils;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;



public abstract class AbstractProcessableTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private List<ProcessableRowStates> rowStates;
	private int rowPointer;
	private int breakpointColumn;
	private int stateColumn;
	private int failureMessageColumn;

	public AbstractProcessableTableModel() {
		rowPointer = 0;
		breakpointColumn = 0;
		stateColumn = 1;
		failureMessageColumn = 2;
	}
	
	public abstract Object getRowAtPointer();
	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == getBreakpointColumn()) {
			if (breakpointAt(rowIndex)) {
				return rowIndex + "(BP)";
			}
			return rowIndex;
		}
		if (columnIndex == getStateColumn()) {
			return rowStateAt(rowIndex);
		}
		if (isErrorCell(columnIndex)) {
			if (rowStateAt(rowIndex) == RowState.FAILED) {
				return messageAt(rowIndex);
			}
		}
		return null;
	}
	protected boolean isErrorCell(int columnIndex) {
		return columnIndex == getColumnCount() - 1;
	}
	public void initRowStates() {
		int rowCount = getRowCount();
		rowStates = new ArrayList<ProcessableRowStates>(rowCount);
		for (int i = 0; i < rowCount; i++) {
			rowStates.add(new ProcessableRowStates());
		}
	}

	public void prepareFirstRow() {
		if (hasFirstRow()) {
			rowPointer = 0;
			ProcessableRowStates row = rowStates.get(rowPointer);
			row.setState(RowState.WAIT);
		}
	}
	
	private boolean hasFirstRow() {
		return CollectionUtils.isNotEmpty(rowStates);
	}

	public boolean breakpointAt(int rowindex) {
		return rowStates.get(rowindex).hasBreakpoint();
	}

	public boolean breakpointAtPointer() {
		return rowStates.get(rowPointer).hasBreakpoint();
	}

	public RowState rowStateAt(int rowindex) {
		return rowStates.get(rowindex).getState();
	}

	public void setRowStateAtPointer(RowState state) {
		rowStates.get(rowPointer).setState(state);
	}

	public void rowPointerNext() {
		rowPointer++;
	}

	public void rowPointerPrevious() {
		rowPointer--;
	}

	public int getPointer() {
		return rowPointer;
	}

	public boolean isBeforePointer(int index) {
		return index < rowPointer;
	}

	public boolean isAfterPointer(int index) {
		return index > rowPointer;
	}

	public boolean isPointer(int index) {
		return index == rowPointer;
	}

	public RowState getPointerState() {
		return rowStateAt(rowPointer);
	}

	public void publishResult(RowState state, String message) {
		rowStates.get(rowPointer).setState(state);
		rowStates.get(rowPointer).setFailureMessage(message);
	}

	public void removeBreakpointAtPointer() {
		rowStates.get(rowPointer).setHasBreakpoint(false);
	}

	public void toggleBreakpoint(int row) {
		rowStates.get(row).toggleBreakpoint();
		fireTableRowsUpdated(row, row);
	}

	public void updatePointerRow(int cell) {
		fireTableCellUpdated(rowPointer, cell);
	}

	public void updateRow(int index) {
		fireTableRowsUpdated(index, index);
	}

	public void updatePointerRow() {
		updateRow(rowPointer);
	}

	public boolean hasMoreRows() {
		return rowPointer + 1 < getRowCount();
	}

	public boolean pointsToLastRow() {
		return getRowCount() - 1 == rowPointer;
	}

	public String messageAt(int index) {
		return rowStates.get(index).getFailureMessage();
	}

	public int getBreakpointColumn() {
		return breakpointColumn;
	}

	public void setBreakpointColumn(int breakpointColumn) {
		this.breakpointColumn = breakpointColumn;
	}

	public int getStateColumn() {
		return stateColumn;
	}

	public void setStateColumn(int stateColumn) {
		this.stateColumn = stateColumn;
	}

	public int getFailureMessageColumn() {
		return failureMessageColumn;
	}

	public void setFailureMessageColumn(int failureMessageColumn) {
		this.failureMessageColumn = failureMessageColumn;
	}
	
	public void addRowState(int index) {
		rowStates.add(index, new ProcessableRowStates());
	}
	public void deleteRowState(int index) {
		rowStates.remove(index);
	}

	public void resetProcessableCounter() {
		rowPointer = 0;
	}
}