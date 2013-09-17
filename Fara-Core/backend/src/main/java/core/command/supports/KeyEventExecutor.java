package core.command.supports;

import java.awt.event.KeyEvent;

/**
 * Special UndoRedoSupport for ui classes.
 */
public interface KeyEventExecutor {

  /**
   * Executes a KeyEvent
   *
   * @param event KeyEvent which should be executed
   * @return true if the key event is executed successfully, false else
   */
  public boolean execute(KeyEvent event);
}
