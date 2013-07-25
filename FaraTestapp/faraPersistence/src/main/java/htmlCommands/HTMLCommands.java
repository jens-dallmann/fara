package htmlCommands;

import java.io.File;

import commandUtils.ParseUtils;

import core.service.FitIOService;
import core.service.exceptions.FitIOException;
import fit.Parse;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class HTMLCommands implements HasCommands {

	@FitCommand({ "HTML file in which the table is", "the row in the table",
			"the column in the table", "the expected string" })
	public CommandResult checkTableCellInRessourceFile(String fileName,
			String tableRow, String tableColumn, String expected) {
		CommandResult result = new CommandResult();
		int row = ParseUtils.readIntegerInput(tableRow, result);
		int column = ParseUtils.readIntegerInput(tableColumn, result);
		File file = new File(fileName);

		if (file.exists()) {
			Parse fileContent = readFileContent(file, result);
			Parse rows = fileContent.parts;
			Parse at = rows.at(1, row - 1, column - 1);
			String actualContent = at.text();
			if (expected.equals(actualContent)) {
				result.setResultState(CommandResultState.RIGHT);
			} else {
				result.setFailureMessage("Wrong content in cell: (row: "
						+ tableRow + ")(column: " + "tableColumn"
						+ "). Expected was: " + expected + " but found: "
						+ actualContent);
				result.setResultState(CommandResultState.WRONG);
				result.setWrongParameterNumber(4);
			}
		} else {
			result.setFailureMessage("The file: " + fileName
					+ " does not exist.");
			result.setWrongParameterNumber(1);
			result.setResultState(CommandResultState.WRONG);
		}
		return result;
	}

	private Parse readFileContent(File file, CommandResult result) {
		FitIOService service = new FitIOService();
		Parse test = null;
		try {
			test = service.readTest(file);
		} catch (FitIOException e) {
			result.setFailureMessage(e.getMessage());
			result.setResultState(CommandResultState.WRONG);
			result.setWrongParameterNumber(1);
		}
		return test;
	}
}
