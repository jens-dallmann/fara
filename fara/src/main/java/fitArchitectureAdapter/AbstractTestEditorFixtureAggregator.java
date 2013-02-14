package fitArchitectureAdapter;

import fit.Parse;
import fitArchitectureAdapter.container.CommandResult;
import interfaces.DoRowsListener;
import interfaces.FaraTestEditor;

import java.lang.reflect.InvocationTargetException;

import testEditor.FaraTestEditorImpl;

public abstract class AbstractTestEditorFixtureAggregator extends AbstractActionFixtureAggregator implements DoRowsListener{
	private FaraTestEditor testEditor;
	public AbstractTestEditorFixtureAggregator() {
		this.testEditor = new FaraTestEditorImpl();
	}
	/**
	 * Init method which initializes the map and calls the adding of the fixture
	 * objects
	 * also it inits the test editor.
	 */
	public void init() {
		super.init();
	}
	@Override
	public void doTable(Parse table) {
		testEditor.startTestEditor();
		testEditor.injectTable(table);
		testEditor.registerListener(this);
		super.doTable(table);
	}
	@Override
	public void doRows(Parse rows) {
		
	}
	@Override
	protected void handleErrorMessages(Parse cell, String errorMessage) {
		testEditor.publishResult(CommandResultState.WRONG.toString(), errorMessage);
	}
	@Override
	protected CommandResult callMethod(String text)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		CommandResult result = super.callMethod(text);
		testEditor.publishResult(result.getResultState().toString(),
				result.getFailureMessage());
		return result;
	}
	
	@Override
	public void doNextRow(Parse parse) {
		doRow(parse);
	}
}
