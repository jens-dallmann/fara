package core;

public interface ProcessService {
	
	public void registerProcessListener(ProcessListener listener);
	public void removeProcessListener(ProcessListener listener);
	public void doNextStep(Object row);
}
