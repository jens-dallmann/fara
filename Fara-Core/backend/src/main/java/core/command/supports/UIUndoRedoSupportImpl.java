package core.command.supports;

import java.awt.event.KeyEvent;

import core.command.Command;

public class UIUndoRedoSupportImpl extends UndoRedoSupportImpl implements UIUndoRedoSupport {
    private CommandFactory commandFactory;

    public UIUndoRedoSupportImpl(CommandFactory commandFactory) {

        this.commandFactory = commandFactory;
    }
	@Override
	public boolean execute(KeyEvent event) {
		int keyCode = event.getKeyCode();
		if(isUndoEvent(keyCode)) {
			return undo();
		}
		else if(isRedoEvent(keyCode)) {
			return redo();
		}
		else {
			Command command = commandFactory.getCommand(event);
			if(command != null)  {
				return execute(command);
			}
		}
		return false;
	}

	private boolean isRedoEvent(int keyCode) {
		return keyCode == KeyEvent.VK_Y;
	}

	private boolean isUndoEvent(int keyCode) {
		return keyCode == KeyEvent.VK_Z;
	}

}
