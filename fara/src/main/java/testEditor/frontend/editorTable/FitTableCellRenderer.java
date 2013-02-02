package testEditor.frontend.editorTable;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import testEditor.frontend.editorTable.model.FitRowTableModel;
import testEditor.frontend.editorTable.model.Row;


public class FitTableCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		decorateStateCell(table, row, column);
		decorateBreakpoint(table, row, column);
		return super.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, column);
	}

	private void decorateBreakpoint(JTable table, int row, int column) {
		if (column == FitRowTableModel.NUMBER_CELL) {
			FitRowTableModel model = extractFitModel(table);
			Row rowModel = model.getRow(row);
			if (rowModel.hasBreakpoint()) {
				setBackground(Color.ORANGE);
			} else {
				setBackground(Color.WHITE);
			}
		}
	}

	private FitRowTableModel extractFitModel(JTable table) {
		TableModel model = table.getModel();
		if (model instanceof FitRowTableModel) {
			return (FitRowTableModel) model;
		}
		return null;
	}

	private void decorateStateCell(JTable table, int row, int column) {
		if (column == FitRowTableModel.STATE_CELL) {
			FitRowTableModel fitModel = extractFitModel(table);
			Row rowObj = fitModel.getRow(row);
			if (rowObj.isProcessing()) {
				setBackground(Color.CYAN);
			} else if (rowObj.isSuccess()) {
				setBackground(Color.GREEN);
			} else if (rowObj.hasFailed()) {
				setBackground(Color.RED);
			} else if (rowObj.isWaiting()) {
				setBackground(Color.LIGHT_GRAY);
			} else if (rowObj.isSkipped()) {
				setBackground(Color.YELLOW);
			} 
			else {
				setBackground(Color.WHITE);
			}
		} else {
			setBackground(Color.WHITE);
		}
	}

}
