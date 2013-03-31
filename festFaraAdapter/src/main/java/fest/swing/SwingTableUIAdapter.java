package fest.swing;

import org.fest.swing.fixture.JTableFixture;

import fest.FestResultBuilder;
import fest.interfaces.TableUIAdapter;
import fest.swing.operators.RelationalOperator;
import fest.swing.operators.RelationalOperatorDispatcher;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class SwingTableUIAdapter implements HasCommands, TableUIAdapter {

	private SwingFrameWrapper _frameWrapper;

	public SwingTableUIAdapter(SwingFrameWrapper frameWrapper) {
		_frameWrapper = frameWrapper;
	}

	@FitCommand({})
	public CommandResult checkTableRowCount(String tableName, String operator,
			String expected) {
		CommandResult result = new CommandResult();
		JTableFixture table = findTable(tableName, result);
		if (result.getResultState() != CommandResultState.WRONG) {
			int rowCountInput = readIntegerInput(expected, result);
			int rowCount = table.rowCount();
			if (result.getResultState() != CommandResultState.WRONG) {
				RelationalOperator relationalOperator = findOperator(operator);
				if (relationalOperator != null) {
					boolean evaluationResult = relationalOperator.evaluate(
							rowCount, rowCountInput);
					if (evaluationResult) {
						result.setResultState(CommandResultState.RIGHT);
					} else {
						result.setFailureMessage(("" + rowCount) + operator
								+ expected);
						result.setWrongParameterNumber(0);
						result.setResultState(CommandResultState.WRONG);
					}
				} else {
					result.setResultState(CommandResultState.WRONG);
					result.setFailureMessage("No valid Operator");
					result.setWrongParameterNumber(2);
				}
			}
		}
		return result;
	}

	private int readIntegerInput(String expected, CommandResult result) {
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

	private JTableFixture findTable(String tableName, CommandResult result) {
		try {
			return _frameWrapper.getFrameFixture().table(tableName);
		} catch (Exception e) {
			result = FestResultBuilder.buildWrongResultComponentFailure(result,
					tableName);
			return null;
		}
	}

	private RelationalOperator findOperator(String operator) {
		RelationalOperatorDispatcher dispatcher = new RelationalOperatorDispatcher();

		return dispatcher.findOperator(operator);
	}
}
