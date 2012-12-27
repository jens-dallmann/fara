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

import java.awt.Component;

import javax.swing.JCheckBox;

import org.fest.swing.fixture.JCheckBoxFixture;

import fest.FestResultBuilder;
import fest.interfaces.CheckboxUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FITCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class SwingCheckboxUIAdapter implements CheckboxUIAdapter, HasCommands{

	private SwingFrameWrapper frameWrapper;
	
	public SwingCheckboxUIAdapter(SwingFrameWrapper wrapper) {
		frameWrapper = wrapper;
	}
	
	@FITCommand
	@Override
	public CommandResult selectCheckbox(String checkboxName) {
		CommandResult result = new CommandResult();
		Component component = frameWrapper.findComponentByName(checkboxName);
		if (component instanceof JCheckBox) {
			JCheckBox checkbox = (JCheckBox) component;
			JCheckBoxFixture fixture = new JCheckBoxFixture(frameWrapper.getRobot(), checkbox);
			fixture.check();
			result.setResultState(CommandResultState.RIGHT);
		} else {
			FestResultBuilder.buildWrongResultComponentFailure(result, checkboxName);
		}
		return result;
	}
}
