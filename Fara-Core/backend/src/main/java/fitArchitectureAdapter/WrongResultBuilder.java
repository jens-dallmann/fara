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

import fitArchitectureAdapter.container.CommandResult;

/**
 * Builds wrong command results.
 * 
 * @author jens.dallmann
 */
public class WrongResultBuilder {

	/**
	 * Modifies the passed Command Result by setting all values.
	 * 
	 * @param result the result which should be modified
	 * @param text the failure message
	 * @param wrongParameterNumber the parameter which is wrong
	 * @return the modified command result
	 */
	public static CommandResult buildWrongResult(CommandResult result, String text, int wrongParameterNumber) {
		result.setResultState(CommandResultState.WRONG);
		result.setFailureMessage(text);
		result.setWrongParameterNumber(wrongParameterNumber);
		return result;
	}
}
