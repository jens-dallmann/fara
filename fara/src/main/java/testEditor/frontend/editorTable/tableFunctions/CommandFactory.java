package testEditor.frontend.editorTable.tableFunctions;

import java.awt.event.KeyEvent;

import javax.swing.JTable;

import testEditor.frontend.editorTable.FitRowTableModel;
import testEditor.frontend.editorTable.tableFunctions.commands.DeleteLineCommand;
import testEditor.frontend.editorTable.tableFunctions.commands.NewLineCommand;
import testEditor.frontend.editorTable.tableFunctions.commands.model.DeleteLineCommandModel;
import testEditor.frontend.editorTable.tableFunctions.commands.model.NewLineCommandModel;
import core.command.Command;
import fit.Parse;


public class CommandFactory {
 	public static Command getCommand(KeyEvent event) {
		JTable table = (JTable) event.getSource();
		if(event.getKeyCode() == KeyEvent.VK_R) {
			return createNewLineCommand(table);
		}
		else if (event.getKeyCode() == KeyEvent.VK_D) {
			return createDeleteLineCommand(table);
		}
		return null;
	}

	private static Command createNewLineCommand(JTable table) {
		int firstSelectedRow = table.getSelectedRow();
		int selectedRowCount = table.getSelectedRowCount();
		NewLineCommandModel model = new NewLineCommandModel(firstSelectedRow, selectedRowCount);
		return new NewLineCommand(table, model);
	}

	private static Command createDeleteLineCommand(JTable table) {
		int[] selectedRows = table.getSelectedRows();
		Parse[] models = new Parse[selectedRows.length];
		for(int i = 0; i < selectedRows.length; i++) {
			FitRowTableModel tableModel = (FitRowTableModel) table.getModel();
			models[i] = tableModel.getRow(selectedRows[i]);
		}
		DeleteLineCommandModel model = new DeleteLineCommandModel(models, selectedRows);
		return new DeleteLineCommand(table,model);
	}
}
