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
	
	public FitTableController(Parse rows) {
		model = new FitRowTableModel(rows);
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
	
	public JComponent getPanel() {
		return ui.getPanel();
	}

	public Parse startRowProcessing() {
		return model.startProcessActualRow();
	}

	public void actualRowProcessed(RowState state, String message) {
		model.actualRowProcessed(state, message);
	}

	public boolean hasMoreRows() {
		return model.hasMoreRows();
	}

	public boolean rowHasBreakpoint() {
		return model.rowHasBreakpoint();
	}

	public int getSelectedRowIndex() {
		return ui.getTable().getSelectedRow();
	}

	public void jumpTo(int selectedRow) {
		model.jumpTo(selectedRow);
	}
}
