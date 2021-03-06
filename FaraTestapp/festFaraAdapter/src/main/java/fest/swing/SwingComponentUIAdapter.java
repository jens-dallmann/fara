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
import fest.interfaces.ComponentUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.query.ComponentEnabledQuery;
import org.fest.swing.query.ComponentVisibleQuery;

import java.awt.*;

public class SwingComponentUIAdapter implements ComponentUIAdapter, HasCommands {

  private SwingFrameWrapper frameWrapper;

  public SwingComponentUIAdapter(SwingFrameWrapper wrapper) {
    frameWrapper = wrapper;
  }

  @FitCommand({"Component name which should be enabled"})
  @Override
  public CommandResult checkEnabled(String componentName) {
    CommandResult result = new CommandResult();
    Component component = allocateComponent(componentName, result);
    if (isResultStateWrong(result)) {
      if (isEnabled(component)) {
        result.setResultState(CommandResultState.RIGHT);
      } else {
        FestResultBuilder.buildWrongResultWrongState(result);
      }
    } else {
      FestResultBuilder.buildWrongResultComponentFailure(result,
              componentName);
    }
    return result;
  }

  private boolean isResultStateWrong(CommandResult result) {
    return result.getResultState() != CommandResultState.WRONG;
  }

  private Component allocateComponent(String componentName, CommandResult result) {
    try {
      Component component = frameWrapper.getFrameFixture().robot.finder().findByName(componentName);
      return component;
    } catch (ComponentLookupException e) {
      FestResultBuilder.buildWrongResultComponentFailure(result, componentName);
      return null;
    }
  }

  @FitCommand({"Component name which should not be enabled"})
  @Override
  public CommandResult checkDisabled(String componentName) {
    CommandResult result = new CommandResult();
    Component component = allocateComponent(componentName, result);
    if (isResultStateWrong(result)) {
      if (!isEnabled(component)) {
        result.setResultState(CommandResultState.RIGHT);
      } else {
        FestResultBuilder.buildWrongResultWrongState(result);
      }
    } else {
      FestResultBuilder.buildWrongResultComponentFailure(result,
              componentName);
    }
    return result;
  }

  private boolean isEnabled(Component component) {
    return ComponentEnabledQuery.isEnabled(component);
  }

  @FitCommand({"The component name which should be visible"})
  @Override
  public CommandResult checkVisible(String componentName) {
    CommandResult result = new CommandResult();
    Component component = allocateComponent(componentName, result);
    if (isResultStateWrong(result)) {
      if (isVisible(component)) {
        result.setResultState(CommandResultState.RIGHT);
      } else {
        FestResultBuilder.buildWrongResultWrongState(result);
      }
    } else {
      FestResultBuilder.buildWrongResultComponentFailure(result,
              componentName);
    }
    return result;
  }

  private boolean isVisible(Component component) {
    return ComponentVisibleQuery.isVisible(component);
  }
}
