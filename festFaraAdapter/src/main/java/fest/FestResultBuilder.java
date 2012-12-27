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
package fest;

import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.container.CommandResult;

public class FestResultBuilder {

	public static CommandResult buildWrongResultComponentFailure(CommandResult result, String componentName) {
		result.setResultState(CommandResultState.WRONG);
		result.setFailureMessage("No correct component found for: "+componentName);
		result.setWrongParameterNumber(1);
		return result;
	}
	public static CommandResult buildWrongResultWrongState(CommandResult result) {
		result.setResultState(CommandResultState.WRONG);
		result.setFailureMessage("Wrong State");
		result.setWrongParameterNumber(0);
		return result;
	}
}
