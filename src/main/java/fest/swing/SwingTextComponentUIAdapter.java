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

import javax.swing.text.JTextComponent;

import org.fest.swing.fixture.JTextComponentFixture;

import fest.FestResultBuilder;
import fest.interfaces.TextComponentUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FITCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class SwingTextComponentUIAdapter implements TextComponentUIAdapter, HasCommands {
	
	private SwingFrameWrapper frameWrapper;
	
	public SwingTextComponentUIAdapter(SwingFrameWrapper wrapper) {
		frameWrapper = wrapper;
	}
	
	@FITCommand
	@Override
	public CommandResult setText(String textField, String text) {
		CommandResult result = new CommandResult();
		Component component = frameWrapper.findComponentByName(textField);
		if (component instanceof JTextComponent) {
			JTextComponentFixture componentFixture = new JTextComponentFixture(
					frameWrapper.getRobot(), textField);
			componentFixture.setText(text);
			result.setResultState(CommandResultState.RIGHT);
		} else {
			FestResultBuilder.buildWrongResultComponentFailure(result, textField);
		}
		return result;
	}
}
