package interfaces;

import fit.Parse;


public interface DoNextRowObservable {
	public void registerListener(DoRowsListener listener);

	public abstract void informListenerNextRow(Parse nextRow);
}
