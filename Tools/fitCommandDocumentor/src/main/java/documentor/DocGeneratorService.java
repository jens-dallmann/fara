package documentor;

import core.service.FileService;
import core.service.exceptions.CreateFileException;
import core.service.exceptions.WriteFileException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocGeneratorService {

  private FileService fileService;

  public DocGeneratorService() {
      fileService = new FileService();
  }

  public void generateDocsByClasses(DocPathNamePair targetDescription, List<Class<?>> classes) {
    List<FitCommandDoc> pairs = new ArrayList<FitCommandDoc>();
    for (Class<?> clazz : classes) {
      ClassFileProcessor classFileProcessor = new ClassFileProcessor();
      pairs.addAll(classFileProcessor.process(clazz));
    }
    generateDocs(targetDescription.getHtmlFileName(), pairs);
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
}
