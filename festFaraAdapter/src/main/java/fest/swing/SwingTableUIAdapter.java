package fest.swing;

import org.fest.swing.fixture.JTableFixture;

import fest.FestResultBuilder;
import fest.interfaces.TableUIAdapter;
import fest.swing.operators.RelationalOperatorEvaluator;
import fest.swing.utils.ParseUtils;
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
			int rowCountInput = ParseUtils.readIntegerInput(expected, result);
			int rowCount = table.rowCount();
			if (result.getResultState() != CommandResultState.WRONG) {
				RelationalOperatorEvaluator.evaluateOperation(operator, rowCount, rowCountInput, result);
			}
		}
		return result;
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

	
}
