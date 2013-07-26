package core;

public interface ProcessService {
	
	public void registerResultListener(ProcessResultListener listener);
	public void removeResultListener(ProcessResultListener listener);
	public void doNextStep(Object row);
}
