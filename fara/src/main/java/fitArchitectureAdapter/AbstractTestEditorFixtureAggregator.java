package fitArchitectureAdapter;

import fit.Parse;
import fitArchitectureAdapter.container.CommandResult;
import interfaces.DoRowsListener;
import interfaces.FaraTestEditor;

import java.lang.reflect.InvocationTargetException;

import testEditor.FaraTestEditorImpl;

public abstract class AbstractTestEditorFixtureAggregator extends AbstractActionFixtureAggregator implements DoRowsListener{
	private FaraTestEditor testEditor;
	private boolean finished;
	/**
	 * Init method which initializes the map and calls the adding of the fixture
	 * objects
	 */
	public void init() {
		super.init();
		this.testEditor = new FaraTestEditorImpl();
	}
	@Override
	public void doRows(Parse rows) {
		testEditor.startTestEditor(rows);
		testEditor.registerListener(this);
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
	
	@Override
	public void finish() {
		finished = true;
	}
}