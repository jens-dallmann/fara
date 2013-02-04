package interfaces;

import fit.Parse;

public interface FaraTestEditor extends DoNextRowObservable{

	public abstract void startTestEditor(Parse rows);
	
	public abstract void publishResult(String state, String message);

}