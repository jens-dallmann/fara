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
			JTextComponentFixture componentFixture = allocateTextComponent(textField, result);
		if(result.getResultState() != CommandResultState.WRONG) {
			String textInTextfield = componentFixture.text();

			if (text.equals(textInTextfield)) {
				result.setResultState(CommandResultState.RIGHT);
			} else {
				FestResultBuilder.buildWrongResultWrongText(result);
			}
		} 
		return result;
	}

	private JTextComponentFixture allocateTextComponent(String textField, CommandResult result) {
		try {
			return frameWrapper.getFrameFixture().textBox(textField);
		}
		catch (ComponentLookupException e) {
			FestResultBuilder.buildWrongResultComponentFailure(result, textField);
			return null;
		}
	}

	@FitCommand({ "the name of the text field", "the text to set" })
	@Override
	public CommandResult setText(String textField, String text) {
		CommandResult result = new CommandResult();
		JTextComponentFixture componentFixture = allocateTextComponent(textField, result);
		if (result.getResultState() != CommandResultState.WRONG) {
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
	public CommandResult checkEditable(String textField) {
		CommandResult result = new CommandResult();
		JTextComponentFixture component = allocateTextComponent(textField, result);
		if (result.getResultState() != CommandResultState.WRONG) {
			JTextComponent textComponent = (JTextComponent) component.target;
			if (isEditable(textComponent)) {
				result.setResultState(CommandResultState.RIGHT);
			} else {
				FestResultBuilder.buildWrongResultWrongState(result);
			}
		} else {
			FestResultBuilder.buildWrongResultComponentFailure(result,
					textField);
		}

		return result;
	}

	@FitCommand({ "Name of the text component" })
	@Override
	public CommandResult checkNotEditable(String textField) {
		CommandResult result = new CommandResult();
		JTextComponentFixture component = allocateTextComponent(textField, result);
		if (result.getResultState() != CommandResultState.WRONG) {
			JTextComponent textComponent = component.target;
			if (!isEditable(textComponent)) {
				result.setResultState(CommandResultState.RIGHT);
			} else {
				FestResultBuilder.buildWrongResultWrongState(result);
			}
		} else {
			FestResultBuilder.buildWrongResultComponentFailure(result,
					textField);
		}

		return result;
	}

	private boolean isEditable(JTextComponent textComponent) {
		return JTextComponentEditableGuiQuery.isEditable(textComponent);
	}
}
