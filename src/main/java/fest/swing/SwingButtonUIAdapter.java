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

import javax.swing.JButton;

import org.fest.swing.fixture.JButtonFixture;

import fest.FestResultBuilder;
import fest.interfaces.ButtonUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FITCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class SwingButtonUIAdapter implements ButtonUIAdapter, HasCommands {

	private SwingFrameWrapper _frameWrapper;

	public SwingButtonUIAdapter(SwingFrameWrapper frameWrapper) {
		_frameWrapper = frameWrapper;
	}

	@FITCommand
	@Override
	public CommandResult pressButton(String buttonName) {
		CommandResult result = new CommandResult();
		Component component = null;
		component = _frameWrapper.findComponentByName(buttonName);
		if(component == null) {
			component = _frameWrapper.findButtonByText(buttonName);
		}

		if (component instanceof JButton) {
			JButton button = (JButton) component;
			JButtonFixture buttonFixture = new JButtonFixture(
					_frameWrapper.getRobot(), button);
			buttonFixture.click();
			result.setResultState(CommandResultState.RIGHT);
		} else {
			FestResultBuilder.buildWrongResultComponentFailure(result,
					buttonName);
		}
		return result;
	}
}
