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

import javax.swing.AbstractButton;

import fest.FestResultBuilder;
import fest.interfaces.AbstractButtonUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FITCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class SwingAbstractButtonUIAdapter implements AbstractButtonUIAdapter, HasCommands{

	private SwingFrameWrapper _frameWrapper;

	public SwingAbstractButtonUIAdapter(SwingFrameWrapper frameWrapper) {
		_frameWrapper = frameWrapper;
	}
	
	@FITCommand
	@Override
	public CommandResult checkSelected(String componentName){
		CommandResult result = new CommandResult();
		Component component = _frameWrapper.findComponentByName(componentName);
		if (component instanceof AbstractButton) {
			AbstractButton fixture = (AbstractButton) component;
			if(fixture.isSelected()) {
				result.setResultState(CommandResultState.RIGHT);
			}
			else {
				FestResultBuilder.buildWrongResultWrongState(result);
			}
		} else {
			FestResultBuilder.buildWrongResultComponentFailure(result, componentName);
		}
		return result;
	}

	@FITCommand
	@Override
	public CommandResult checkNotSelected(String componentName){
		CommandResult result = new CommandResult();
		Component component = _frameWrapper.findComponentByName(componentName);
		if (component instanceof AbstractButton) {
			AbstractButton fixture = (AbstractButton) component;
			if(!fixture.isSelected()) {
				result.setResultState(CommandResultState.RIGHT);
			}
			else {
				FestResultBuilder.buildWrongResultWrongState(result);
			}
		} else {
			FestResultBuilder.buildWrongResultComponentFailure(result, componentName);
		}
		return result;
	}
}
