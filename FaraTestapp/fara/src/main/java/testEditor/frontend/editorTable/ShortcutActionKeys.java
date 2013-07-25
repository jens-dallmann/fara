package testEditor.frontend.editorTable;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

public enum ShortcutActionKeys {
	NEW_LINE ("Adds the count of selected rows as empty rows after last selected row", KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK)),
	DELETE_LINE ("Deletes all selected rows", KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
	
	private final KeyStroke key;
	private final String description;

	ShortcutActionKeys(String description, KeyStroke key) {
		this.description = description;
		this.key = key;
		
	}
	
	public KeyStroke getKey() {
		return key;
	}
	public String getDescription() {
		return description;
	}
}
