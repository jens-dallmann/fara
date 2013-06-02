package testEditor.frontend.editorTable.tableFunctions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import testEditor.frontend.editorTable.FitRowTableModel;
import testEditor.frontend.editorTable.ShortcutActionKeys;
import testEditor.frontend.editorTable.tableFunctions.shortcutActions.DeleteLinesAction;
import testEditor.frontend.editorTable.tableFunctions.shortcutActions.NewLineAction;

public class TableFunctionShortCutInitializer {
	private JTable sourceTable;
	private FitRowTableModel model;
	private final int CTRL = InputEvent.CTRL_MASK;
	
	public TableFunctionShortCutInitializer(JTable table) {
		sourceTable = table;
		model = (FitRowTableModel) table.getModel();
		init();
	}

	private void init() {
		InputMap inputMap = sourceTable.getInputMap();
		ActionMap actionMap = sourceTable.getActionMap();
		
		registerNewLineAction(inputMap, actionMap);
		registerDeleteLineAction(inputMap, actionMap);
	}

	private void registerDeleteLineAction(InputMap inputMap, ActionMap actionMap) {
		KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_D, CTRL);
		inputMap.put(key,ShortcutActionKeys.DELETE_LINE);
		actionMap.put(ShortcutActionKeys.DELETE_LINE, new DeleteLinesAction(model));
	}

	private void registerNewLineAction(InputMap inputMap, ActionMap actionMap) {
		KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_L, CTRL);
		inputMap.put(key,
				ShortcutActionKeys.NEW_LINE);
		actionMap.put(ShortcutActionKeys.NEW_LINE, new NewLineAction(model));
	}
}
