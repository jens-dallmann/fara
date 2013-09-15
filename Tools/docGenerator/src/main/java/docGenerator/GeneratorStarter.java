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

package docGenerator;

import docGenerator.frontend.MainWindowController;
import docGenerator.services.DocGeneratorService;

public class GeneratorStarter {

  public static void main(String[] args) {
    DocGeneratorService service = new DocGeneratorService();
    new MainWindowController(service);
  }
}
