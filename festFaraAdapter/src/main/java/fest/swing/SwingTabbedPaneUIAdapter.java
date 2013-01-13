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

import javax.swing.JTabbedPane;

import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.fixture.JTabbedPaneFixture;

import fest.FestResultBuilder;
import fest.interfaces.TabbedPaneUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.WrongResultBuilder;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class SwingTabbedPaneUIAdapter implements TabbedPaneUIAdapter,
		HasCommands {

	private SwingFrameWrapper frameWrapper;

	public SwingTabbedPaneUIAdapter(SwingFrameWrapper wrapper) {
		frameWrapper = wrapper;
	}

	@FitCommand({"name of the tab","the index of the tab"})
	@Override
	public CommandResult changeTab(String componentName, String index) {
		CommandResult result = new CommandResult();
		int parsedIndex = Integer.parseInt(index);
		Component component = frameWrapper.findComponentByName(componentName);
		if (component instanceof JTabbedPane) {
			JTabbedPane tabbedPane = (JTabbedPane) component;
			JTabbedPaneFixture fixture = new JTabbedPaneFixture(
					frameWrapper.getRobot(), tabbedPane);
			try {
				fixture.selectTab(parsedIndex);
				result.setResultState(CommandResultState.RIGHT);
			} catch (LocationUnavailableException lue) {
				WrongResultBuilder.buildWrongResult(result, "Title not found in tab: "+index, 2);
			}
		} else {
			FestResultBuilder.buildWrongResultComponentFailure(result,
					componentName);
		}
		return result;
	}
}
