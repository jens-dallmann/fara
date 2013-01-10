package docGenerator.model;

import java.util.ArrayList;
import java.util.List;

public class FitCommandDoc implements Comparable<FitCommandDoc>{
	private String commandName;
	private List<String> commandParams;
	
	public FitCommandDoc() {
		commandParams = new ArrayList<String>();
	}
	
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}
	
	public List<String> getCommandParams() {
		return commandParams;
	}
	
	public String getCommandName() {
		return commandName;
	}

	public void setCommandParams(List<String> commandParams) {
		this.commandParams = commandParams;
	}

	@Override
	public int compareTo(FitCommandDoc o) {
		return o.getCommandName().compareTo(commandName);
	}
}
