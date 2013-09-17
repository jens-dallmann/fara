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
package docGenerator.fileFilter;

import java.io.File;
import java.io.FileFilter;

public class FolderFileFilter implements FileFilter {

  @Override
  public boolean accept(File pathname) {
    return pathname != null && pathname.isDirectory();
  }

}
