package firstIntegrationTest;

import fitArchitectureAdapter.interfaces.HasCommands;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.annotations.FitCommand;

public class SecondCommand implements HasCommands {

    @FitCommand({"another command description", "another command description second parameter"})
    public CommandResult anotherCommand(String firstParameter, String secondParameter) {
        return new CommandResult();
    }
}