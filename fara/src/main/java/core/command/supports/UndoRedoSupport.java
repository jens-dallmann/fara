package core.command.supports;

import core.command.Command;

public interface UndoRedoSupport {
	
	public boolean execute(Command command);
	
	public boolean undo();
	
	public boolean redo();
	
	public boolean canUndo();
	
	public boolean canRedo();
}
