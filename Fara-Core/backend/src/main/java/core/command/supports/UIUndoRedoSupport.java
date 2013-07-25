package core.command.supports;

import java.awt.event.KeyEvent;

public interface UIUndoRedoSupport extends UndoRedoSupport {

	public boolean execute(KeyEvent event);
}
