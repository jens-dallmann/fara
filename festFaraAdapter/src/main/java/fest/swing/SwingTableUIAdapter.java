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

	@FitCommand({"The name of the table which rows should be checked", "The operator which is used for check. For valid Operators see Operatorlist", "The number used for comparison"})
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
	@FitCommand({"Name of the table in which the cell should be checked",
		"The row number",
		"The column number",
		"The expected String"})
	public CommandResult checkTableCell(String tableName, String row, String column, String expected) {
		CommandResult result = new CommandResult();
		int rowNumber = Integer.parseInt(row);
		int columnNumber = Integer.parseInt(column);
		JTableFixture table = findTable(tableName, result);
		String[][] contents = table.contents();
		String actual = contents[rowNumber-1][columnNumber-1];
		if(expected.equals(actual)) {
			result.setResultState(CommandResultState.RIGHT);
		}
		else {
			result.setFailureMessage("The text in row "+row+" and column "+column+" is \""+actual+"\" and does not match \""+expected+"\"");
			result.setResultState(CommandResultState.WRONG);
			result.setWrongParameterNumber(4);
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
