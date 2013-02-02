package testEditor;

import fit.Parse;
import interfaces.DoRowsListener;
import interfaces.FaraTestEditor;

import java.util.ArrayList;
import java.util.List;

import testEditor.frontend.TestEditorController;

public class FaraTestEditorImpl implements FaraTestEditor{
	private TestEditorController testEditor;
	private List<DoRowsListener> listeners;
	
	public FaraTestEditorImpl() {
		listeners = new ArrayList<DoRowsListener>();
	}
	
	/* (non-Javadoc)
	 * @see testEditor.FaraTestEditor#startTestEditor(fit.Parse)
	 */
	@Override
	public void startTestEditor(Parse rows) {
		testEditor = new TestEditorController(this, rows);
	}
	
	/* (non-Javadoc)
	 * @see testEditor.FaraTestEditor#startProcessNextRow()
	 */
	@Override
	public Parse startProcessNextRow() {
		return testEditor.startProcessNextRow();
	}
	
	/* (non-Javadoc)
	 * @see testEditor.FaraTestEditor#canProcessNext()
	 */
	@Override
	public boolean canProcessNext() {
		return testEditor.shouldStepForward() || testEditor.shouldPlay();
	}
	
	/* (non-Javadoc)
	 * @see testEditor.FaraTestEditor#hasMoreRows()
	 */
	@Override
	public boolean hasMoreRows() {
		return testEditor.hasMoreRows();
	}

	/* (non-Javadoc)
	 * @see testEditor.FaraTestEditor#publishResult(java.lang.String, java.lang.String)
	 */
	@Override
	public void publishResult(String state, String message) {
		testEditor.setResult(state, message);
	}

	@Override
	public void registerListener(DoRowsListener listener) {
		listeners.add(listener);
	}

	@Override
	public void informListenerNextRow(Parse nextRow) {
		for(DoRowsListener listener: listeners) {
			listener.doNextRow(nextRow);
		}
	}
}
