package core;

import directoryCrawler.ActionType;
import directoryCrawler.CrawlerAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Will load classes from the target folder by take classes paths and cut of everything before classes folder including
 * classes folder. This means it extracts the package path for the class from target folder.
 */
public class LoadClassCrawlerAction implements CrawlerAction {
  private static final Logger LOG = LoggerFactory.getLogger(LoadClassCrawlerAction.class);
  private ClassLoader loader;
  private List<Class<?>> allClasses;

  public LoadClassCrawlerAction(ClassLoader loader) {
    assert loader != null;

    this.loader = loader;
    allClasses = new ArrayList<Class<?>>();
  }

  @Override
  public void execute(File input) {
    assert isExecutable(input);
    String classPath = buildClassPath(input.getAbsolutePath());
    try {
      Class<?> loadClass = loader.loadClass(classPath);
      allClasses.add(loadClass);
      LOG.info("Successfully loaded class: " + loadClass.getName());
    } catch (ClassNotFoundException e) {
      LOG.warn("Skipped Class " + input.getAbsolutePath() + ": Can not load it from class loader. The class to load by ClassLoader is: " + classPath);
    }
  }

  @Override
  public ActionType getType() {
    return ActionType.FILE;
  }

  @Override
  public String getName() {
    return this.getClass().getName();
  }

  @Override
  public boolean isExecutable(File file) {
    return file != null && file.getAbsolutePath().endsWith(".class");
  }

  /**
   * builds up the class which should be loaded from the class loader.
   * It parses pathes from classes/any/path.class to a format the class loader can load
   * by replacing / by dots and cut of the everything before the classes folder.
   *
   * @param absolutePath
   * @return
   */
  private static String buildClassPath(String absolutePath) {
    List<String> pathFromClassFolder = buildPathUptoClassesFolder(absolutePath);

    String lastElement = pathFromClassFolder.get(pathFromClassFolder.size() - 1);
    pathFromClassFolder.remove(pathFromClassFolder.size() - 1);
    String newLastElement = lastElement.substring(0, lastElement.lastIndexOf(".class"));
    pathFromClassFolder.add(newLastElement);

    return rebuildClassPath(pathFromClassFolder);
  }

  private static List<String> buildPathUptoClassesFolder(String absolutePath) {
    String pattern = Pattern.quote(System.getProperty("file.separator"));
    String[] splittedPath = absolutePath.split(pattern);

    List<String> pathFromClassFolder = new ArrayList<String>();
    boolean isClassesFolderPassed = false;
    for (String onePathElement : splittedPath) {
      if (isClassesFolderPassed) {
        pathFromClassFolder.add(onePathElement);
      }
      if (onePathElement.equals("classes")) {
        isClassesFolderPassed = true;
      }
    }
    return pathFromClassFolder;
  }

  private static String rebuildClassPath(List<String> pathFromClassFolder) {
    StringBuffer classPath = new StringBuffer();
    for (int i = 0; i < pathFromClassFolder.size(); i++) {
      String onePathElement = pathFromClassFolder.get(i);
      if (i != 0) {
        classPath.append(".");
      }
      classPath.append(onePathElement);
    }
    return classPath.toString();
  }

  public List<Class<?>> getResult() {
    return allClasses;
  }
}
