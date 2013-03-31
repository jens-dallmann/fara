package fixture;

import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class DummyCommands implements HasCommands{

	@FitCommand({})
	public CommandResult anyCommandWithOneParameter(String oneParameter) {
		CommandResult result = new CommandResult();
		result.setResultState(CommandResultState.RIGHT);
		return result;
	}
}
