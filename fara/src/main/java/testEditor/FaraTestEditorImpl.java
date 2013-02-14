package testEditor;

import fit.Parse;
import interfaces.DoRowsListener;
import interfaces.FaraTestEditor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import testEditor.frontend.TestEditorController;

public class FaraTestEditorImpl implements FaraTestEditor{
	private TestEditorController testEditor;
	private List<DoRowsListener> listeners;
	
	public FaraTestEditorImpl() {
		listeners = new ArrayList<DoRowsListener>();
	}
	
	@Override
	public void startTestEditor() {
		testEditor = new TestEditorController(this);
	}
	
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
			ExecutorService executor = Executors.newSingleThreadExecutor();
			final ListenerCall caller = new ListenerCall(listener, nextRow);
			executor.execute(new Runnable() {
				@Override
				public void run() {
					caller.doCall();
				}
			});
		}
	}

	@Override
	public void injectTable(Parse table) {
		testEditor.initTable(table);
	}
}
