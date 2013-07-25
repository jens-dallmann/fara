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
import org.fest.swing.fixture.JButtonFixture;

import fest.FestResultBuilder;
import fest.interfaces.ButtonUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class SwingButtonUIAdapter implements ButtonUIAdapter, HasCommands {

	private SwingFrameWrapper _frameWrapper;

	public SwingButtonUIAdapter(SwingFrameWrapper frameWrapper) {
		_frameWrapper = frameWrapper;
	}

	@FitCommand({"The name of the button which should be pressed"})
	@Override
	public CommandResult pressButton(String buttonName) {
		CommandResult result = new CommandResult();
		JButtonFixture button = allocateButton(buttonName, result);
		if (result.getResultState() != CommandResultState.WRONG) {
			button.click();
			result.setResultState(CommandResultState.RIGHT);
		} else {
			FestResultBuilder.buildWrongResultComponentFailure(result,
					buttonName);
		}
		return result;
	}

	private JButtonFixture allocateButton(String buttonName, CommandResult result) {
		try {
			return _frameWrapper.getFrameFixture().button(buttonName);
		}
		catch(ComponentLookupException e) {
			FestResultBuilder.buildWrongResultComponentFailure(result, buttonName);
			e.printStackTrace();
			return null;
		}
	}
}
