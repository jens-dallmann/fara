package testEditor.frontend.editorTable.model;

import testEditor.frontend.editorTable.RowState;
import fit.Parse;

/**
 * Represents a row in the table. A row in the table is defined by the parse
 * object which has references to the cells. Also this parse object is extended
 * by the row state, if it has a breakpoint and if state is failed the message
 * why it failed.
 * 
 * @author jens.dallmann
 */
public class Row {
	private RowState state;
	private Parse originalRow;
	private String message;
	private boolean hasBreakpoint;

	public Row(Parse row) {
		this.originalRow = row;
		state = RowState.INITIAL;
	}

	public int countCells() {
		int cellCount = 0;
		Parse oneCell = getCells();
		while (oneCell != null) {
			cellCount++;
			oneCell = oneCell.more;
		}
		return cellCount;
	}

	public Parse getCell(int cellNumber) {
		Parse oneCell = getCells();
		for (int i = 0; i < cellNumber; i++) {
			oneCell = oneCell.more;
		}
		return oneCell;
	}

	public void setProcessing() {
		state = RowState.PROCESSING;
	}

	public boolean isProcessing() {
		return state == RowState.PROCESSING;
	}

	public Parse getCells() {
		return originalRow.parts;
	}

	public Parse getOriginalRow() {
		return originalRow;
	}

	public boolean isSuccess() {
		return RowState.SUCCESS == state;
	}

	public boolean hasFailed() {
		return RowState.FAILED == state;
	}

	public void setState(RowState state2) {
		state = state2;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RowState getState() {
		return state;
	}

	public String getMessage() {
		return message;
	}

	public boolean isWaiting() {
		return RowState.WAIT == state;
	}

	public void toggleBreakpoint() {
		hasBreakpoint = !hasBreakpoint;
	}

	public boolean hasBreakpoint() {
		return hasBreakpoint;
	}

	public boolean isSkipped() {
		return RowState.SKIPPED == state;
	}

	public void removeBreakpoint() {
		hasBreakpoint = false;
	}

	public void setCellText(int columnIndex, String aValue) {
		getCells().at(columnIndex).body = aValue;
	}
}
