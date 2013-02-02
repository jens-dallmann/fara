package interfaces;

import fit.Parse;

public interface DoRowsListener {
	public void doNextRow(Parse parse);
	public void finish();
}
