package testEditor.frontend.editorTable;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;

import testEditor.frontend.editorTable.model.FitRowTableModel;
import testEditor.frontend.editorTable.model.Row;
import fit.Parse;

public class FitTableController {

	private FitTableUI ui;
	private FitRowTableModel model;
	
	public FitTableController(Parse rows) {
		model = createTableModel(rows);
		ui = new FitTableUI(model);
		ui.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = ui.getTable();
				Point p = e.getPoint();
				int row = table.rowAtPoint(p);
				int column = table.columnAtPoint(p);
				model.toggleBreakpoint(row,column);
				table.getSelectionModel().clearSelection();
			}
		});
	}
	
	private FitRowTableModel createTableModel(Parse rows) {
		FitRowTableModel model = new FitRowTableModel();
		createRowListInModel(rows, model);
		model.prepareFirstRow();
		model.calculateColumnCount();
		return model;
	}

	private void createRowListInModel(Parse rows, FitRowTableModel model) {
		List<Row> rowsList = new ArrayList<Row>();
		model.setRows(rowsList);
		while (rows != null) {
			rowsList.add(new Row(rows));
			rows = rows.more;
		}
	}

	public JComponent getPanel() {
		return ui.getPanel();
	}

	public Parse startRowProcessing() {
		Row actualRow = model.getActualRow();
		actualRow.setProcessing();
		model.updateActualRow(FitRowTableModel.STATE_CELL);
		return actualRow.getOriginalRow();
	}

	public void actualRowProcessed(RowState state, String message) {
		Row row = model.getActualRow();
		row.setState(state);
		if(state == RowState.FAILED) {
			row.setMessage(message);
		}
		model.updateActualRow();
	}

	public boolean hasMoreRows() {
		return model.hasMoreRows();
	}

	public boolean rowHasBreakpoint() {
		return model.actualRowHasBreakpoint();
	}

	public int getSelectedRowIndex() {
		return ui.getTable().getSelectedRow();
	}

	public void jumpTo() {
		int selectedRow = ui.getTable().getSelectedRow();
		if (selectedRow >= 0) {
			while (!model.isActualRow(selectedRow)) {
				model.getActualRow().setState(RowState.SKIPPED);
				if (model.isAfterActualRow(selectedRow)) {
					model.increaseActualRowIndex();
				} else if (model.isBeforeActualRow(selectedRow)) {
					model.decreaseActualRowIndex();
				}
			}
			model.getActualRow().setState(RowState.WAIT);
			model.fireTableRowsUpdated(0, model.getRowCount() - 1);
		}
	}
	public void enableNextRow() {
		if(model.hasMoreRows()) {
			model.increaseActualRowIndex();
			Row row = model.getActualRow();
			row.setState(RowState.WAIT);
			model.updateActualRow();
		}
	}

	public void removeRowBreakpoint() {
		model.removeBreakpointAtActualRow();
	}

	public List<Row> getRows() {
		return model.getRows();
	}

	public String tableAsHtml() {
		List<Row> rows = getRows();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<table border='1'>");
		for(Row oneRow: rows) {
			Parse originalRow = oneRow.getOriginalRow();
			buffer.append(originalRow.tag);
			buffer.append(buildCells(originalRow.parts));
			buffer.append(originalRow.end);
		}
		buffer.append("</table>");
		return buffer.toString();
	}

	private String buildCells(Parse parts) {
		StringBuffer buffer = new StringBuffer();
		Parse cells = parts;
		while(cells != null) {
			
			buffer.append(cells.tag);
			buffer.append(cells.text());
			buffer.append(cells.end);
			cells = cells.more;
		}
		
		return buffer.toString();
	}

	public boolean isLastRow() {
		return model.isLastRow();
	}
}
