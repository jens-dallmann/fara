package fest.swing.utils;

import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.container.CommandResult;

public class ParseUtils {
	public static int readIntegerInput(String expected, CommandResult result) {
		int rowCountInput = 0;
		try {
			rowCountInput = Integer.parseInt(expected);
		} catch (NumberFormatException e) {
			result.setFailureMessage("Not a correct number");
			result.setWrongParameterNumber(3);
			result.setResultState(CommandResultState.WRONG);
		}
		return rowCountInput;
	}
}
