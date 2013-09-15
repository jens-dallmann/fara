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
package docGenerator.model;

import java.util.ArrayList;
import java.util.List;

public class FitCommandDoc implements Comparable<FitCommandDoc> {
  private String className;
  private String commandName;
  private List<String> commandParams;

  public FitCommandDoc(String className, String commandName) {
    commandParams = new ArrayList<String>();
    this.className = className;
    this.commandName = commandName;
  }

  public String getClassName() {
    return className;
  }

  public List<String> getCommandParams() {
    return commandParams;
  }

  public String getCommandName() {
    return commandName;
  }

  public void setCommandParams(List<String> commandParams) {
    this.commandParams = commandParams;
  }

  @Override
  public int compareTo(FitCommandDoc o) {
    if(equals(o)) {
      return 0;
    }
    else {
      int compareToOfClassName = o.getClassName().compareTo(className);
      if(compareToOfClassName != 0) {
        return compareToOfClassName;
      }
      else {
        return o.getCommandName().compareTo(commandName);
      }
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof FitCommandDoc) {
      FitCommandDoc fitCommandDoc = (FitCommandDoc) obj;
      if (className.equals(fitCommandDoc.className) && commandName.equals(fitCommandDoc.commandName)) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return className.hashCode() + commandName.hashCode();
  }
}