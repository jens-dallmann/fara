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
package fitArchitectureAdapter;

import core.ProcessListener;
import fit.ActionFixture;
import fit.Parse;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.container.InstanceMethodPair;
import fitArchitectureAdapter.interfaces.HasCommands;
import org.apache.commons.lang3.StringEscapeUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The core class of the new fit architecture. It Overrides the action fixture
 * and for special overrides the doCells method. In doCells will the command be
 * called with the parameters. At first the command is in the first cell of the
 * row and the parameters of the command are in the following cells. The java
 * method which should be called is allocated out of the added fixture classes.
 * It must be a method annotated with @FitCommand. If a method could be
 * allocated the parameters will be counted and as many parameters as the method
 * has are tried to read out of the html table. After this the java-method will
 * be called and a result will be expected. If no error occurs the result will
 * be processed.
 *
 * @author jens.dallmann
 */
public abstract class AbstractActionFixtureAggregator extends ActionFixture {

  private Map<String, InstanceMethodPair> commands;
  private List<ProcessListener> listeners;

  public AbstractActionFixtureAggregator() {
    listeners = new ArrayList<ProcessListener>();
  }

  /**
   * Init method which initializes the map and calls the adding of the fixture
   * objects
   */
  public void init() {
    commands = new HashMap<String, InstanceMethodPair>();
    addFixtureObjects();
  }

  @Override
  public void doCells(Parse cells) {
    this.cells = cells;
    CommandResult result = null;
    String commandName = cells.text();
    try {
      result = callMethod(commandName);
    } catch (IllegalArgumentException e) {
      handleErrorMessages(cells, "Command not found: " + commandName);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
      handleErrorMessages(cells, "To Less or to much arguments");
    }

    if (result != null) {
      processResult(result);
    }
  }

  protected void handleErrorMessages(Parse cell, String errorMessage) {
    wrong(cell, errorMessage);
    publishResult(CommandResultState.WRONG.toString(), errorMessage);
  }

  protected CommandResult callMethod(String text) throws IllegalArgumentException,
          IllegalAccessException, InvocationTargetException {
    InstanceMethodPair pair;
    pair = commands.get(text);

    if (pair == null) {
      throw new IllegalArgumentException("Command " + text + "not found");
    }

    Method command = pair.getMethod();
    Object fixtureObject = pair.getFixtureInstance();
    Class<?>[] parameterTypes = command.getParameterTypes();
    int numberOfParameters = parameterTypes.length;
    Object[] actualParameters = buildActualParameters(numberOfParameters);
    Object result = command.invoke(fixtureObject, actualParameters);
    if (result instanceof CommandResult) {
      return (CommandResult) result;
    }
    return null;
  }

  private Object[] buildActualParameters(int numberOfParameters) {
    Object[] actualParameters = new Object[numberOfParameters];
    Parse parameter = cells.more;
    for (int i = 0; i < numberOfParameters && parameter != null; parameter = parameter.more, i++) {
      actualParameters[i] = StringEscapeUtils.unescapeHtml4(parameter
              .text());
    }
    return actualParameters;
  }

  private void processResult(CommandResult commandResult) {
    CommandResultState resultState = commandResult.getResultState();

    if (CommandResultState.RIGHT == resultState) {
      processRight();
    } else if (CommandResultState.WRONG == resultState) {
      int parameterNumer = commandResult.getWrongParameterNumber();
      String message = commandResult.getFailureMessage();
      processWrong(parameterNumer, message);
    }
    publishResult(commandResult.getResultState().toString(),
            commandResult.getFailureMessage());
  }

  private void processWrong(int parameterNumber, String message) {
    Parse wrongCell = cells;
    for (int i = 0; i < parameterNumber; i++) {
      wrongCell = cells.more;
    }
    wrong(wrongCell, message);
  }

  private void processRight() {
    right(cells);
  }

  /**
   * Adds an object to the map ob objects with fit commands. In this process
   * the class will be searched for methods annotated with
   *
   * @param commandObject instance of the class which provides methods.
   * @FITCommand. If one hass been found it will be wrapped with the passed
   * class instance in the map. The name of the method is the
   * commands name.
   */
  public void addCommandObject(HasCommands commandObject) {
    Method[] methods = extractMethods(commandObject);
    for (Method oneClassMethod : methods) {
      boolean isFitCommand = isFITCommand(oneClassMethod);
      if (isFitCommand) {
        String methodName = oneClassMethod.getName();
        if (isNotInMap(methodName)) {
          InstanceMethodPair pair = new InstanceMethodPair(
                  commandObject, oneClassMethod);
          commands.put(methodName, pair);
          fireAddedCommandToMap(pair, methodName);
        }
      }
    }
  }

  private void fireAddedCommandToMap(InstanceMethodPair pair, String methodname) {
    for (ProcessListener listener : listeners) {
      listener.addedCommandToMap(pair, methodname);
    }
  }

  private Method[] extractMethods(Object commandObject) {
    Class<?> commandClass = commandObject.getClass();
    Method[] methods = commandClass.getMethods();
    return methods;
  }

  private boolean isNotInMap(String methodName) {
    return !commands.containsKey(methodName);
  }

  private boolean isFITCommand(Method oneClassMethod) {
    FitCommand annotation = oneClassMethod.getAnnotation(FitCommand.class);
    return annotation != null;
  }

  /**
   * Method to implement to add new Fixture Objects it will be triggered the
   * init method. This method has to be called after constructor
   * initialization
   */
  public abstract void addFixtureObjects();

  public void registerProcessListener(ProcessListener listener) {
    listeners.add(listener);
  }

  public void removeProcessListener(ProcessListener listener) {
    listeners.remove(listener);
  }

  public void publishResult(String state, String failureMessage) {
    for (ProcessListener listener : listeners) {
      listener.publishResult(state, failureMessage);
    }
  }
}
