package material;

import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class HasOneCommand implements HasCommands {

  @FitCommand("Parameter 1")
  public CommandResult oneMethod(String parameterOne) {
    return null;
  }
}
