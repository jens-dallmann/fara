/*******************************************************************************
 * Copyright (c) 2013 Jens Dallmann.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jens Dallmann - initial API and implementation
 ******************************************************************************/
package docGenerator.model;

import java.util.ArrayList;
import java.util.List;

public class FitCommandDoc implements Comparable<FitCommandDoc>{
	private String className;
	private String commandName;
	private List<String> commandParams;
	
	public FitCommandDoc() {
		commandParams = new ArrayList<String>();
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassName() {
		return className;
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
		return o.getCommandName().compareTo(className+ "" +commandName);
	}
}