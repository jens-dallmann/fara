package interfaces;

import fit.Parse;

public interface FaraTestEditor extends DoNextRowObservable{

	public abstract void startTestEditor(Parse rows);

	public abstract Parse startProcessNextRow();

	public abstract boolean canProcessNext();

	public abstract boolean hasMoreRows();

	public abstract void publishResult(String state, String message);

}