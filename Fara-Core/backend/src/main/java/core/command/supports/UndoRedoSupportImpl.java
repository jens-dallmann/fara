package core.command.supports;

import core.command.Command;
import core.command.CommandStack;

public class UndoRedoSupportImpl implements UndoRedoSupport{
	
	private CommandStack executed;
	
	private CommandStack undone;
	
	public UndoRedoSupportImpl() {
		executed = new CommandStack();
		undone = new CommandStack();
	}
	
	@Override
	public boolean execute(Command command) {
		boolean isExecuted = command.execute();
		if(isExecuted) {
			undone.clear();
			executed.push(command);
		}
		return isExecuted;
	}

	@Override
	public boolean undo() {
		if(canUndo()) {
			Command commandToUndo = executed.pop();
			boolean isUndone = commandToUndo.undo();
			if(isUndone) {
				undone.push(commandToUndo);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean redo() {
		if(canRedo()) {
			Command commandToExecute = undone.pop();
			boolean isExecuted = commandToExecute.execute();
			if(isExecuted) {
				executed.push(commandToExecute);
				
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canUndo() {
		return !executed.isEmpty();
	}

	@Override
	public boolean canRedo() {
		return !undone.isEmpty();
	}
}
