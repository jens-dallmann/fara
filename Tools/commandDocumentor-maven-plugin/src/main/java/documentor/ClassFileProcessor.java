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
package documentor;

import fitArchitectureAdapter.annotations.FitCommand;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class ClassFileProcessor implements FileProcessor {

  @Override
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
    String commandName = method.getName();
    String name = method.getDeclaringClass().getName();
    int lastIndexOf = name.lastIndexOf(".");
    String className = name.substring(lastIndexOf + 1, name.length());
    FitCommandDoc doc = new FitCommandDoc(className, commandName);
    List<String> commandParams = buildCommandParams(method);
    doc.setCommandParams(commandParams);
    return doc;
  }

  private List<String> buildCommandParams(Method method) {
    List<String> commandParams = new ArrayList<String>();
    FitCommand parameters = method.getAnnotation(FitCommand.class);
    if (parameters != null) {
      for (String parameter : parameters.value()) {
        if (isNotBlank(parameter)) {
          commandParams.add(parameter);
        }
      }
    }
    return commandParams;
  }

  private boolean isFitCommand(Method method) {
    FitCommand annotation = method.getAnnotation(FitCommand.class);
    return annotation != null;
  }
}
