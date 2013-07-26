/*******************************************************************************
 * Copyright (c) 2013 Jens Dallmann.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jens Dallmann - initial API and implementation
 ******************************************************************************/
package docGenerator.processors.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import docGenerator.model.FitCommandDoc;
import docGenerator.processors.FileProcessor;
import fitArchitectureAdapter.annotations.FitCommand;

public class ClassFileProcessor implements FileProcessor {

	private ClassLoader classLoader;

	public ClassFileProcessor() {
	}
	public ClassFileProcessor(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}


	@Override
	public List<FitCommandDoc> process(String fileToProcess) {
		Class<?> loadedClass = loadClass(fileToProcess);
		return process(loadedClass);
	}

	public List<FitCommandDoc> process(Class<?> clazz) {
		return collectFitCommands(clazz);
	}
	private List<FitCommandDoc> collectFitCommands(Class<?> loadedClass) {
		List<FitCommandDoc> commands = new ArrayList<FitCommandDoc>();
		Method[] methods = loadedClass.getMethods();
		for (Method method : methods) {
			if (isFitCommand(method)) {
				FitCommandDoc commandDoc = buildCommandDoc(method);
				commands.add(commandDoc);
			}
		}
		return commands;
	}

	private FitCommandDoc buildCommandDoc(Method method) {
		FitCommandDoc doc = new FitCommandDoc();
		doc.setCommandName(method.getName());
		
		String name = method.getDeclaringClass().getName();
		int lastIndexOf = name.lastIndexOf(".");
		doc.setClassName(name.substring(lastIndexOf+1, name.length()));
		List<String> commandParams = buildCommandParams(method);
		doc.setCommandParams(commandParams);
		return doc;
	}

	private List<String> buildCommandParams(Method method) {
		List<String> commandParams = new ArrayList<String>();
		FitCommand parameters = method.getAnnotation(FitCommand.class);
		if (parameters != null) {
			for (String parameter : parameters.value()) {
				commandParams.add(parameter);
			}
		}
		return commandParams;
	}

	private boolean isFitCommand(Method method) {
		FitCommand annotation = method.getAnnotation(FitCommand.class);
		return annotation != null;
	} 

	private Class<?> loadClass(String fileToProcess) {
		Class<?> loadClass = null;
		try {
			loadClass = classLoader.loadClass(fileToProcess);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return loadClass;
	}
}
