package core;

import fitArchitectureAdapter.container.InstanceMethodPair;

public interface ProcessListener {
	public void publishResult(String state, String message);

    public void addedCommandToMap(InstanceMethodPair pair, String commandName);

}