package fitArchitectureAdapter;

import java.lang.reflect.InvocationTargetException;

import core.ProcessService;
import fit.Parse;
import fitArchitectureAdapter.container.CommandResult;

public abstract class AbstractProcessableFixtureAggregator extends
		AbstractActionFixtureAggregator implements ProcessService {


	/**
	 * Init method which initializes the map and calls the adding of the fixture
	 * objects also it inits the test editor.
	 */
	public void init() {
		super.init();
	}

	@Override
	public void doTable(Parse table) {
		super.doTable(table);
	}

	@Override
	public void doRows(Parse rows) {
		super.doRows(rows);
	}

	@Override
	protected void handleErrorMessages(Parse cell, String errorMessage) {
		publishResult(CommandResultState.WRONG.toString(), errorMessage);
	}

	@Override
	protected CommandResult callMethod(String text)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		CommandResult result = super.callMethod(text);

		return result;
	}

	public void doNextStep(Object row) {
		if (row instanceof Parse) {
			Parse parse = (Parse) row;
			doRow(parse);
		}
	}


}
