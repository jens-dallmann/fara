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

import docGenerator.fileFilter.ClassFileFilter;
import docGenerator.fileFilter.FolderFileFilter;
import docGenerator.model.FitCommandDoc;
import docGenerator.processors.FileProcessor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderFileProcessor implements FileProcessor {

  private final ClassFileProcessor fileProcessor;
  private final String rootPath;

  public FolderFileProcessor(ClassFileProcessor classFileProcessor, String rootPath) {
    this.fileProcessor = classFileProcessor;
    this.rootPath = rootPath;
  }

  @Override
  public List<FitCommandDoc> process(String pathCommaSeparatedProcess) {
    List<FitCommandDoc> documentation = new ArrayList<FitCommandDoc>();
    documentation.addAll(processFilesInFolder(pathCommaSeparatedProcess));
    documentation.addAll(processFoldersInFolder(pathCommaSeparatedProcess));
    return documentation;
  }

  private List<FitCommandDoc> processFoldersInFolder(String pathCommaSeparated) {
    File file = createFile(pathCommaSeparated);
    File[] folderFiles = file.listFiles(new FolderFileFilter());
    List<FitCommandDoc> newCommandDocs = processFolderFiles(pathCommaSeparated, folderFiles);
    return newCommandDocs;
  }

  private File createFile(String pathCommaSeparated) {
    File file = new File(rootPath + File.separator + pathCommaSeparated);
    return file;
  }

  private List<FitCommandDoc> processFolderFiles(String oldPath, File[] folderFiles) {
    List<FitCommandDoc> commands = new ArrayList<FitCommandDoc>();
    if (folderFiles != null) {
      for (File file : folderFiles) {
        String newPath = oldPath + File.separator + file.getName();
        commands.addAll(process(newPath));
      }
    }
    return commands;
  }

  private List<FitCommandDoc> processFilesInFolder(String commaseparatedPath) {
    File folder = createFile(commaseparatedPath);
    File[] classFiles = folder.listFiles(new ClassFileFilter());
    if (classFiles != null) {
      return processClassFiles(commaseparatedPath, classFiles);
    } else {
      return new ArrayList<FitCommandDoc>();
    }
  }

  private List<FitCommandDoc> processClassFiles(String oldPath, File[] classFiles) {
    List<FitCommandDoc> commandDocs = new ArrayList<FitCommandDoc>();
    for (File classFile : classFiles) {
      String newPath = oldPath + File.separator + trimmName(classFile.getName());
      List<FitCommandDoc> retrievedCommandDocs = fileProcessor
              .process(newPath);
      commandDocs.addAll(retrievedCommandDocs);
    }
    return commandDocs;
  }

  private String trimmName(String name) {
    return name.replaceAll(".class", "");
  }
}
