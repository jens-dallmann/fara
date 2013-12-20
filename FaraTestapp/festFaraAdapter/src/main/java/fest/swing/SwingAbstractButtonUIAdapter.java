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

import fest.FestResultBuilder;
import fest.interfaces.AbstractButtonUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

import javax.swing.*;

public class SwingAbstractButtonUIAdapter implements AbstractButtonUIAdapter, HasCommands {

  private SwingFrameWrapper _frameWrapper;

  public SwingAbstractButtonUIAdapter(SwingFrameWrapper frameWrapper) {
    _frameWrapper = frameWrapper;
  }

  @FitCommand({"The name of the AbstractButton which should be selected"})
  @Override
  public CommandResult checkSelected(String componentName) {
    CommandResult result = new CommandResult();
    AbstractButton component = allocateButton(componentName, result);
    if (result.getResultState() != CommandResultState.WRONG) {
      AbstractButton fixture = (AbstractButton) component;
      if (fixture.isSelected()) {
        result.setResultState(CommandResultState.RIGHT);
      } else {
        FestResultBuilder.buildWrongResultWrongState(result);
      }
    } else {
      FestResultBuilder.buildWrongResultComponentFailure(result, componentName);
    }
    return result;
  }

  @FitCommand({"The name of the AbstractButton which should NOT be selected"})
  @Override
  public CommandResult checkNotSelected(String componentName) {
    CommandResult result = new CommandResult();
    AbstractButton abstractButton = allocateButton(componentName, result);
    if (result.getResultState() != CommandResultState.WRONG) {
      if (!abstractButton.isSelected()) {
        result.setResultState(CommandResultState.RIGHT);
      } else {
        FestResultBuilder.buildWrongResultWrongState(result);
      }
    } else {
      FestResultBuilder.buildWrongResultComponentFailure(result, componentName);
    }
    return result;
  }

  private AbstractButton allocateButton(String componentName,
                                        CommandResult result) {
    try {
      return (AbstractButton) _frameWrapper.getFrameFixture().robot.finder().findByName(componentName);
    } catch (Exception e) {
      FestResultBuilder.buildWrongResultComponentFailure(result, componentName);
      return null;
    }
  }
}
