package testEditor.frontend.editorTable.model;

import testEditor.frontend.editorTable.RowState;
import fit.Parse;

public class Row {
	private RowState state;
	private Parse cells;
	private Parse originalRow;
	private String message;
	private boolean hasBreakpoint;
	
	public Row(Parse row) {
		this.originalRow = row;
		this.cells = row.parts;
		state = RowState.INITIAL;
	}
	public int countCells() {
		int cellCount = 0;
		Parse oneCell = cells;
		while(oneCell != null) {
			cellCount++;
			oneCell = oneCell.more;
		}
		return cellCount;
	}
	
	public Parse getCell(int cellNumber) {
		Parse oneCell = cells;
		for(int i = 0; i < cellNumber; i++) {
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
		return cells;
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
}
