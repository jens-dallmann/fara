package fitArchitectureAdapter.commands;

import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class GeneralCommands implements HasCommands{

	@FitCommand({"comment will be ignored when executed"})
	public CommandResult comment() {
		CommandResult result = new CommandResult();
		result.setResultState(CommandResultState.IGNORE);
		return result;
	}
}
