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
	
	@FitCommand({})
	public CommandResult alwaysSucceed() {
		CommandResult result = new CommandResult();
		result.setResultState(CommandResultState.RIGHT);
		return result;
	}
	
	@FitCommand({})
	public CommandResult alwaysFail() {
		CommandResult result = new CommandResult();
		result.setResultState(CommandResultState.WRONG);
		result.setWrongParameterNumber(0);
		result.setFailureMessage("Returned Error Message");
		return result;
	}
}