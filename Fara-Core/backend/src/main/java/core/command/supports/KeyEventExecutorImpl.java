package core.command.supports;

import core.command.Command;

import java.awt.event.KeyEvent;

/**
 * Executes KeyEvents by delegating for undo/redo to undo redo support
 * or retrieve a command to execute from the command factory
 */
public class KeyEventExecutorImpl implements KeyEventExecutor {

  private CommandFactory commandFactory;
  private UndoRedoSupport undoRedoSupport;

  /**
   * Constructor to inject CommandFactory.
   * UndoRedoSupport will be defaulted to UndoRedoSupportImpl.
   *
   * @param commandFactory factory where to find the commands for key events
   */
  public KeyEventExecutorImpl(CommandFactory commandFactory) {
    this(commandFactory, new UndoRedoSupportImpl());
  }

  /**
   * Constructor to inject CommandFactory and UndoRedoSupport
   *
   * @param commandFactory  factory where to find the commands for key events
   * @param undoRedoSupport the undo redo support to use for undo and redo actions
   */
  public KeyEventExecutorImpl(CommandFactory commandFactory, UndoRedoSupport undoRedoSupport) {
    this.undoRedoSupport = undoRedoSupport;
    this.commandFactory = commandFactory;
  }

  @Override
  public boolean execute(KeyEvent event) {
    int keyCode = event.getKeyCode();
    if (isUndoEvent(keyCode)) {
      return undoRedoSupport.undo();
    } else if (isRedoEvent(keyCode)) {
      return undoRedoSupport.redo();
    } else {
      Command command = commandFactory.getCommand(event);
      if (command != null) {
        return undoRedoSupport.execute(command);
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
