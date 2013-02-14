package testEditor.frontend.editorTable;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JTable;

import testEditor.frontend.editorTable.model.FitRowTableModel;
import fit.Parse;

public class FitTableController {

	private FitTableUI ui;
	private FitRowTableModel model;

	public void init(Parse table) {
		model = createTableModel(table);
		String fixtureName = extractFixtureName(table);
		ui = new FitTableUI(model, fixtureName);
		ui.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable table = ui.getTable();
				Point p = e.getPoint();
				int row = table.rowAtPoint(p);
				int column = table.columnAtPoint(p);
				if (column == FitRowTableModel.NUMBER_CELL) {
					model.toggleBreakpoint(row);
				}
			}
		});
		ui.getFixtureComponent().setFixtureChangedDelegate(
				new FixtureChangedDelegate() {
					@Override
					public void fixtureChanged(String text) {
						model.setFixtureName(text);
					}
				});
	}

	private String extractFixtureName(Parse table) {
		if(table == null) {
			return "";
		}
		else {
			return table.at(0,0,0).text();
		}
	}

	private FitRowTableModel createTableModel(Parse table) {
		FitRowTableModel model = new FitRowTableModel();
		model.setTable(table);
		model.initRowStates();
		model.prepareFirstRow();
		model.calculateColumnCount();
		return model;
	}

	public JComponent getPanel() {
		return ui.getPanel();
	}

	public Parse startRowProcessing() {
		model.setRowStateAtPointer(RowState.PROCESSING);

		return model.getActualRow();
	}

	public void actualRowProcessed(RowState state, String message) {
		if (message == null) {
			message = "";
		}
		model.publishResult(state, message);
		model.updatePointerRow();
	}

	public boolean hasMoreRows() {
		return model.hasMoreRows();
	}

	public boolean rowHasBreakpoint() {
		return model.breakpointAtPointer();
	}

	public int getSelectedRowIndex() {
		return ui.getTable().getSelectedRow();
	}

	public void jumpTo() {
		int selectedRow = ui.getTable().getSelectedRow();
		if (selectedRow >= 0) {
			while (!model.isPointer(selectedRow)) {
				model.setRowStateAtPointer(RowState.SKIPPED);
				if (model.isAfterPointer(selectedRow)) {
					model.rowPointerNext();
				} else if (model.isBeforePointer(selectedRow)) {
					model.rowPointerPrevious();
				}
			}
			model.setRowStateAtPointer(RowState.WAIT);
			model.fireTableRowsUpdated(0, model.getRowCount() - 1);
		}
	}

	public void enableNextRow() {
		if (model.hasMoreRows()) {
			model.rowPointerNext();
			model.setRowStateAtPointer(RowState.WAIT);
			model.updatePointerRow();
		}
	}

	public void removeRowBreakpoint() {
		model.removeBreakpointAtPointer();
	}

	public Parse getRows() {
		return model.getTable().parts;
	}

	public String tableAsHtml() {
		Parse originalRow = getRows();
		StringBuffer buffer = new StringBuffer();
		buffer.append("<table border='1'>");
		while (originalRow != null) {
			buffer.append(originalRow.tag);
			buffer.append(buildCells(originalRow.parts));
			buffer.append(originalRow.end);
			originalRow = originalRow.more;
		}
		buffer.append("</table>");
		return buffer.toString();
	}

	private String buildCells(Parse parts) {
		StringBuffer buffer = new StringBuffer();
		Parse cells = parts;
		while (cells != null) {

			buffer.append(cells.tag);
			buffer.append(cells.text());
			buffer.append(cells.end);
			cells = cells.more;
		}

		return buffer.toString();
	}

	public boolean isLastRow() {
		return model.pointsToLastRow();
	}

	public FitRowTableModel getModel() {
		return model;
	}
}
