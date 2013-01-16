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

import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.fixture.JTextComponentFixture;

import fest.FestResultBuilder;
import fest.driver.JTextComponentEditableGuiQuery;
import fest.interfaces.TextComponentUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class SwingTextComponentUIAdapter implements TextComponentUIAdapter,
		HasCommands {

	private SwingFrameWrapper frameWrapper;

	public SwingTextComponentUIAdapter(SwingFrameWrapper wrapper) {
		frameWrapper = wrapper;
	}

	@FitCommand({ "the name of the text field",
			"the text which the component should contain" })
	@Override
	public CommandResult checkText(String textField, String text) {
		CommandResult result = new CommandResult();
		try {
			JTextComponentFixture componentFixture = frameWrapper
					.getFrameFixture().textBox(textField);
			
			String textInTextfield = componentFixture.text();

			if (text.equals(textInTextfield)) {
				result.setResultState(CommandResultState.RIGHT);
			} else {
				FestResultBuilder.buildWrongResultWrongText(result);
			}
		} catch (ComponentLookupException e) {
			FestResultBuilder.buildWrongResultComponentFailure(result,
					textField);
		}
		return result;
	}

	@FitCommand({ "the name of the text field", "the text to set" })
	@Override
	public CommandResult setText(String textField, String text) {
		CommandResult result = new CommandResult();
		Component component = frameWrapper.findComponentByName(textField);
		if (isTextComponent(component)) {
			JTextComponentFixture componentFixture = createTextComponentFixture(component);
			componentFixture.setText(text);
			result.setResultState(CommandResultState.RIGHT);
		} else {
			FestResultBuilder.buildWrongResultComponentFailure(result,
					textField);
		}
		return result;
	}

	@FitCommand({ "Name of the text component" })
	@Override
	public CommandResult checkEditable(String textfield) {
		CommandResult result = new CommandResult();
		Component component = frameWrapper.findComponentByName(textfield);
		if (isTextComponent(component)) {
			JTextComponent textComponent = (JTextComponent) component;
			if (isEditable(textComponent)) {
				result.setResultState(CommandResultState.RIGHT);
			} else {
				FestResultBuilder.buildWrongResultWrongState(result);
			}
		} else {
			FestResultBuilder.buildWrongResultComponentFailure(result,
					textfield);
		}

		return result;
	}

	@FitCommand({ "Name of the text component" })
	@Override
	public CommandResult checkNotEditable(String textfield) {
		CommandResult result = new CommandResult();
		Component component = frameWrapper.findComponentByName(textfield);
		if (isTextComponent(component)) {
			JTextComponent textComponent = (JTextComponent) component;
			if (!isEditable(textComponent)) {
				result.setResultState(CommandResultState.RIGHT);
			} else {
				FestResultBuilder.buildWrongResultWrongState(result);
			}
		} else {
			FestResultBuilder.buildWrongResultComponentFailure(result,
					textfield);
		}

		return result;
	}

	private boolean isEditable(JTextComponent textComponent) {
		return JTextComponentEditableGuiQuery.isEditable(textComponent);
	}

	private JTextComponentFixture createTextComponentFixture(Component component) {
		JTextComponent textcomponent = (JTextComponent) component;
		JTextComponentFixture fixture = new JTextComponentFixture(
				frameWrapper.getRobot(), textcomponent);
		return fixture;
	}

	private boolean isTextComponent(Component component) {
		return component instanceof JTextComponent;
	}
}
