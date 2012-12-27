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

import javax.swing.JList;

import org.fest.swing.fixture.JListFixture;

import fest.FestResultBuilder;
import fest.interfaces.ListUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FITCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class SwingListUIAdapter implements ListUIAdapter, HasCommands {

	private SwingFrameWrapper frameWrapper;

	public SwingListUIAdapter(SwingFrameWrapper wrapper) {
		frameWrapper = wrapper;
	}

	@FITCommand
	@Override
	public CommandResult selectListItem(String listName, String itemName){
		CommandResult result = new CommandResult();
		Component component = frameWrapper.findComponentByName(listName);
		if (component instanceof JList) {
			JList list = (JList) component;
			JListFixture fixture = new JListFixture(frameWrapper.getRobot(), list);
			fixture.selectItem(itemName);
			result.setResultState(CommandResultState.RIGHT);
		} else {
			FestResultBuilder.buildWrongResultComponentFailure(result, listName);
		}
		return result;
	}
}
