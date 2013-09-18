package docGenerator.services;

import core.service.FileService;
import core.service.exceptions.CreateFileException;
import core.service.exceptions.WriteFileException;
import docGenerator.FileClassLoader;
import docGenerator.HTMLBuilder;
import docGenerator.model.DocPathNamePair;
import docGenerator.model.FitCommandDoc;
import docGenerator.processors.impl.ClassFileProcessor;
import docGenerator.processors.impl.FolderFileProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocGeneratorService {

  private FileService fileService;

  public DocGeneratorService() {

  }

  public DocGeneratorService(FileService service) {
    this.fileService = service;
  }

  public void generateDocs(List<DocPathNamePair> pairs) {
    for (DocPathNamePair pair : pairs) {
      generateDocs(pair.getPath(), pair.getHtmlFileName());
    }
  }

  public void generateDocsByClasses(DocPathNamePair targetDescription, List<Class<?>> classes) {
    List<FitCommandDoc> pairs = new ArrayList<FitCommandDoc>();
    for (Class<?> clazz : classes) {
      ClassFileProcessor classFileProcessor = new ClassFileProcessor();
      pairs.addAll(classFileProcessor.process(clazz));
    }
    generateDocs(targetDescription.getHtmlFileName(), pairs);
  }

  private void generateDocs(String path, String htmlFileName) {
    List<FitCommandDoc> process = collectCommandsForPath(path);
    generateDocs(htmlFileName, process);
  }

  public void generateDocs(String htmlFileName, List<FitCommandDoc> process) {
    Collections.sort(process);
    String buildHtml = new HTMLBuilder().build(process);
    try {
      fileService.writeToFileCreateIfNotExist(htmlFileName + ".html",
          buildHtml);
    }  catch (WriteFileException e) {
      e.printStackTrace();
    } catch (CreateFileException e) {
      e.printStackTrace();
    }
  }

  private static List<FitCommandDoc> collectCommandsForPath(String path) {
    FileClassLoader classLoader = new FileClassLoader(path);
    FolderFileProcessor processor = new FolderFileProcessor(
            new ClassFileProcessor(classLoader), path);
    List<FitCommandDoc> process = processor.process(path);
    return process;
  }
}
