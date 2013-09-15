package core.command.supports;

import core.command.Command;

/**
 * A UndoRedoSupport manages the execution, undo and redo of commands
 */
public interface UndoRedoSupport {

  /**
   * executes the given command
   *
   * @param command the command to execute
   * @return true if the command can be exectued, false else
   */
  public boolean execute(Command command);

  /**
   * undo the last command
   *
   * @return true if the last command has been undone, false else
   */
  public boolean undo();

  /**
   * redo the last undone command
   *
   * @return true if the last command has been redone, false else
   */
  public boolean redo();

  /**
   * Ask the support if undo can be done.
   *
   * @return true if undo can be done, false else
   */
  public boolean canUndo();

  /**
   * Ask the support if redo can be done.
   *
   * @return true if redo can be done, false else
   */
  public boolean canRedo();
}
