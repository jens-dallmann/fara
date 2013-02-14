package fitArchitectureAdapter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import testEditor.frontend.TestEditorController;
import core.processableTable.ProcessResultListener;
import core.processableTable.ProcessService;
import fit.Parse;
import fitArchitectureAdapter.container.CommandResult;

public abstract class AbstractTestEditorFixtureAggregator extends AbstractActionFixtureAggregator implements ProcessService{
	private List<ProcessResultListener> listeners;
	
	public AbstractTestEditorFixtureAggregator() {
		listeners = new ArrayList<ProcessResultListener>();
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
		new TestEditorController(this, table);
		super.doTable(table);
	}
	@Override
	public void doRows(Parse rows) {
		
	}
	@Override
	protected void handleErrorMessages(Parse cell, String errorMessage) {
		publishResultToListeners(CommandResultState.WRONG.toString(), errorMessage);
	}
	@Override
	protected CommandResult callMethod(String text)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		CommandResult result = super.callMethod(text);
		publishResultToListeners(result.getResultState().toString(),
				result.getFailureMessage());
		return result;
	}
	public void doNextStep(Object row) {
		if(row instanceof Parse) {
			Parse parse = (Parse) row;
			doRow(parse);
		}
	}
	
	@Override
	public void registerResultListener(ProcessResultListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void removeResultListener(ProcessResultListener listener) {
		listeners.remove(listener);
	}
	
	public void publishResultToListeners(String state, String message) {
		for(ProcessResultListener listener: listeners) {
			listener.publishResult(state, message);
		}
	}
}
