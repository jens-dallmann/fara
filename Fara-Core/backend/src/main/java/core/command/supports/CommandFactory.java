package core.command.supports;

import core.command.Command;

import java.awt.event.KeyEvent;

public interface CommandFactory {
  public Command getCommand(KeyEvent e);
}
