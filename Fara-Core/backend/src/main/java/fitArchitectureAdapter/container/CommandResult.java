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
package fitArchitectureAdapter.container;

import fitArchitectureAdapter.CommandResultState;

/**
 * Result for every command.
 * It provides the state on the end of the command, the parameter which
 * seems to be wrong. 0 represents the command.
 *
 * @author jens.dallmann
 */
public class CommandResult {

  private CommandResultState resultState;
  private int wrongParameterNumer;
  private String failureMessage;

  public CommandResult() {
    resultState = CommandResultState.IGNORE;
  }

  /**
   * Set the state after the command has been processed
   *
   * @param resultState the state
   */
  public void setResultState(CommandResultState resultState) {
    this.resultState = resultState;
  }

  /**
   * Returns the state after the command has been processed
   *
   * @return resultState
   */
  public CommandResultState getResultState() {
    return resultState;
  }

  /**
   * Sets the parameter number which has been wrong.
   * Could be null if the result state is RIGHT or IGNORE because
   * this will be ignored.
   *
   * @param parameterNumber between 0 for command and count of parameters of the method
   */
  public void setWrongParameterNumber(int parameterNumber) {
    this.wrongParameterNumer = parameterNumber;
  }

  /**
   * Returns the parameter which is wrong.
   *
   * @return int any integer between 0 and the count of parameters of the method
   * @see setWrongParameterNumber
   */
  public int getWrongParameterNumber() {
    return wrongParameterNumer;
  }

  /**
   * Sets the failure message which is displayed in the column of the html
   * fit table.
   *
   * @param failureMessage a failure message to describe what went wrong
   */
  public void setFailureMessage(String failureMessage) {
    this.failureMessage = failureMessage;
  }

  /**
   * Returns the failure message which is displayed in the column of the html
   * fit table.
   *
   * @return failure message
   */
  public String getFailureMessage() {
    return failureMessage;
  }
}
