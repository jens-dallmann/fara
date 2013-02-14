package interfaces;

import fit.Parse;

public interface FaraTestEditor extends DoNextRowObservable{

	public abstract void startTestEditor();
	
	public abstract void publishResult(String state, String message);

	public abstract void injectTable(Parse rows);

}