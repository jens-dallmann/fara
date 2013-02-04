package testEditor;

import fit.Parse;
import interfaces.DoRowsListener;

public class ListenerCall {
	private final DoRowsListener listener;
	private final Parse nextRow;

	public ListenerCall(DoRowsListener listener, Parse nextRow) {
		this.listener = listener;
		this.nextRow = nextRow;
	}
	
	public void doCall() {
		listener.doNextRow(nextRow);
	}
}
