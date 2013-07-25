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

import java.lang.reflect.Method;

import fitArchitectureAdapter.interfaces.HasCommands;

/**
 * Maps an instance of a fit command providing class to one of its methods.
 * 
 * @author jens.dallmann
 */
public class InstanceMethodPair {
	
	private HasCommands fixtureInstance;
	private Method method;

	/**
	 * Initialize with fields 
	 * 
	 * @param instance instance of a class which implements the interface HasCommands
	 * @param method a method which is part of the public methods of the instances class.
	 */
	public InstanceMethodPair(HasCommands instance, Method method) {
		this.fixtureInstance = instance;
		this.method = method;
	}

	/**
	 * returns the method
	 * 
	 * @return the method
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * returns the instance of has commands.
	 * 
	 * @return instance for has commands
	 */
	public HasCommands getFixtureInstance() {
		return fixtureInstance;
	}

	@Override
	public int hashCode() {
		return fixtureInstance.hashCode() + method.hashCode();
	}

	@Override
	public boolean equals(Object otherPair) {
		if (otherPair instanceof InstanceMethodPair) {
			InstanceMethodPair castedPair = (InstanceMethodPair) otherPair;
			return castedPair.getFixtureInstance().equals(fixtureInstance)
					&& castedPair.getMethod().equals(method);
		} else {
			return false;
		}
	}
}
