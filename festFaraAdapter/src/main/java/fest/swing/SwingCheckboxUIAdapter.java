/*******************************************************************************
 * Copyright (c) 2012 Jens Dallmann.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jens Dallmann - initial API and implementation
 ******************************************************************************/
package fest.swing;

import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.fixture.JCheckBoxFixture;

import fest.FestResultBuilder;
import fest.interfaces.CheckboxUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class SwingCheckboxUIAdapter implements CheckboxUIAdapter, HasCommands{

	private SwingFrameWrapper frameWrapper;
	
	public SwingCheckboxUIAdapter(SwingFrameWrapper wrapper) {
		frameWrapper = wrapper;
	}
	
	@FitCommand({"The name of the checkbox which should be selected"})
	@Override
	public CommandResult selectCheckbox(String checkboxName) {
		CommandResult result = new CommandResult();
		JCheckBoxFixture fixture = allocateCheckbox(checkboxName, result);
		if (result.getResultState() != CommandResultState.WRONG) {
			try {
				fixture.check();
				result.setResultState(CommandResultState.RIGHT);
			} 
			catch(IllegalArgumentException e) {
				result.setResultState(CommandResultState.WRONG);
				result.setFailureMessage("Checkbox is not visible or is disabled");
				result.setWrongParameterNumber(1);
			}
		} else {
			FestResultBuilder.buildWrongResultComponentFailure(result, checkboxName);
		}
		return result;
	}

	private JCheckBoxFixture allocateCheckbox(String checkboxName,
			CommandResult result) {
		try {
			return frameWrapper.getFrameFixture().checkBox(checkboxName);
		}
		catch(ComponentLookupException e) {
			FestResultBuilder.buildWrongResultComponentFailure(result, checkboxName);
			return null;
		}
	}
}
