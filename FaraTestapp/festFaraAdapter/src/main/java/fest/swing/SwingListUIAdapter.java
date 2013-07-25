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

import org.apache.commons.lang3.ArrayUtils;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.fixture.JListFixture;

import commandUtils.ParseUtils;

import fest.FestResultBuilder;
import fest.interfaces.ListUIAdapter;
import fest.swing.operators.RelationalOperatorEvaluator;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class SwingListUIAdapter implements ListUIAdapter, HasCommands {

	private SwingFrameWrapper frameWrapper;

	public SwingListUIAdapter(SwingFrameWrapper wrapper) {
		frameWrapper = wrapper;
	}

	@FitCommand({"the name of the list widget","the name of the item in the list"})
	@Override
	public CommandResult selectListItem(String listName, String itemName){
		CommandResult result = new CommandResult();
		JListFixture fixture = allocateList(listName, result);
		if (result.getResultState() != CommandResultState.WRONG) {
			fixture.selectItem(itemName);
			result.setResultState(CommandResultState.RIGHT);
		}
		return result;
	}
	@FitCommand({"the name of the list widget", "the text of the item which should be in this list"})
	@Override
	public CommandResult checkListItemExist(String listName, String itemDescriptor) {
		CommandResult result = new CommandResult();
		JListFixture fixture = allocateList(listName, result);
		if(result.getResultState() != CommandResultState.WRONG) {
			String[] contents = fixture.contents();
			boolean contains = ArrayUtils.contains(contents, itemDescriptor);
			if(contains) {
				result.setResultState(CommandResultState.RIGHT);
			}
			else {
				result.setFailureMessage("Item Element "+itemDescriptor+" not found in List "+listName);
				result.setResultState(CommandResultState.WRONG);
				result.setWrongParameterNumber(2);
			}
		}
		return result;
	}
	@FitCommand({"the name of the list widget", "the text of the item which should be in this list"})
	@Override
	public CommandResult checkListItemCount(String listName, String operator, String expected) {
		CommandResult result = new CommandResult();
		JListFixture fixture = allocateList(listName, result);
		if(result.getResultState() != CommandResultState.WRONG) {
			String[] items = fixture.contents();
			int actualItemCount = items.length;
			int expectedItemCount = ParseUtils.readIntegerInput(expected, result);
			RelationalOperatorEvaluator.evaluateOperation(operator, actualItemCount, expectedItemCount, result);
		}
		return result;
	}
	@FitCommand({"the name of the list widget"})
	@Override
	public CommandResult checkNoListItemSelected(String listName) {
		CommandResult result = new CommandResult();
		JListFixture fixture = allocateList(listName, result);
		if(result.getResultState() != CommandResultState.WRONG) {
			String[] selectedItems = fixture.selection();
			if(ArrayUtils.isEmpty(selectedItems)) {
				result.setResultState(CommandResultState.RIGHT);
			}
			else {
				StringBuffer buffer = new StringBuffer();
				for(String oneSelectedItem: selectedItems) {
					buffer.append(" "+oneSelectedItem);
				}
				result.setFailureMessage("The following items are selected:"+buffer.toString());
				result.setResultState(CommandResultState.WRONG);
				result.setWrongParameterNumber(1);
			}
		}
		return result;
	}
	public JListFixture allocateList(String listName, CommandResult result) {
		try {
			return frameWrapper.getFrameFixture().list(listName);
		} catch(ComponentLookupException e) {
			FestResultBuilder.buildWrongResultComponentFailure(result, listName);
			return null;
		}
	}
}
