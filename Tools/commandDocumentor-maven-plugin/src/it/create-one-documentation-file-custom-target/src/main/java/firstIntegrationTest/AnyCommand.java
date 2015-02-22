package firstIntegrationTest;

import fitArchitectureAdapter.interfaces.HasCommands;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.annotations.FitCommand;

public class AnyCommand implements HasCommands {

    @FitCommand({"firstParameterDescription", "secondParameterDescription"})
    public CommandResult anyCommand(String firstParameter, String secondParameter) {
        return new CommandResult();
    }
}