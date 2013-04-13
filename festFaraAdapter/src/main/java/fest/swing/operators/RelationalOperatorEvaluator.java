package fest.swing.operators;

import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.container.CommandResult;

public class RelationalOperatorEvaluator {
	public static CommandResult evaluateOperation(String operator, int actual, int expected, CommandResult result) {
		RelationalOperator relationalOperator = findOperator(operator);
		if (relationalOperator != null) {
			boolean evaluationResult = relationalOperator.evaluate(
					actual, expected);
			if (evaluationResult) {
				result.setResultState(CommandResultState.RIGHT);
			} else {
				result.setFailureMessage(("" + actual) + operator
						+ expected);
				result.setWrongParameterNumber(0);
				result.setResultState(CommandResultState.WRONG);
			}
		} else {
			result.setResultState(CommandResultState.WRONG);
			result.setFailureMessage("No valid Operator");
			result.setWrongParameterNumber(2);
		}
		
		return result;
	}
	private static RelationalOperator findOperator(String operator) {
		RelationalOperatorDispatcher dispatcher = new RelationalOperatorDispatcher();

		return dispatcher.findOperator(operator);
	}
}
