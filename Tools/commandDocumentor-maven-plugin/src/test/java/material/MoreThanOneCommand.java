package material;

import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class MoreThanOneCommand implements HasCommands {
  @FitCommand("Parameter 1")
  public CommandResult firstMethod(String oneParameter) {
    return null;
  }

  @FitCommand({"Parameter 1", "Parameter 2"})
  public CommandResult secondMethod(String oneParameter, String secondParameter) {
    return null;
  }

  @FitCommand("")
  public CommandResult thirdMethod() {
    return null;
  }
}
