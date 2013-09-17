package core;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Deception
 * Date: 26.07.13
 * Time: 22:00
 * To change this template use File | Settings | File Templates.
 */
public class ClassLoaderUtils {

  public static ClassLoader buildClassLoader(Log logger, ClassLoader parent, List<String> classesToLoad) {
    return new URLClassLoader(buildUrls(logger, classesToLoad), parent);
  }

  private static URL[] buildUrls(Log logger, List<String> classesToLoad) {
    List<URL> urls = new ArrayList<URL>();
    for (Object object : classesToLoad) {
      String path = (String) object;
      try {
        urls.add(new File(path).toURI().toURL());
      } catch (MalformedURLException e) {
        logger.warn("The Class " + path + " can not be loaded because it's not a valid url");
      }
    }
    return urls.toArray(new URL[]{});
  }

  public static ClassLoader addResourceFilesToClasspath(List<Resource> resources, ClassLoader parent) {
    List<URL> resourceUrls = new ArrayList<URL>();
    for (Resource resource : resources) {
      try {
        URL url = new URL(resource.getTargetPath());
        resourceUrls.add(url);
      } catch (MalformedURLException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
    }
    return new URLClassLoader((URL[]) (resourceUrls.toArray(new URL[0])), parent);

  }

  public static List<Class<?>> loadClassesRecursivelyFromDirectory(ClassLoader loader, Log logger, File startDirectory,
                                                                   List<Class<?>> allClasses) throws MojoExecutionException {

    for (File oneFile : startDirectory.listFiles()) {
      if (oneFile.isDirectory()) {
        loadClassesRecursivelyFromDirectory(loader, logger, oneFile, allClasses);
      } else {
        if (oneFile.getAbsolutePath().contains(".class")) {
          String classPath = buildClassPath(oneFile.getAbsolutePath());
          try {
            Class<?> loadClass = loader.loadClass(classPath);
            allClasses.add(loadClass);
            logger.info("Successfully loaded class: " + loadClass.getName());
          } catch (ClassNotFoundException e) {
            logger.warn("Skipped Class " + oneFile.getAbsolutePath() + ": Can not load it from class loader. The class to load by ClassLoader is: " + classPath);
          }
        }
      }
    }
    return allClasses;
  }

  private static String buildClassPath(String absolutePath) {
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
    String lastElement = pathFromClassFolder.get(pathFromClassFolder.size() - 1);
    pathFromClassFolder.remove(pathFromClassFolder.size() - 1);
    String newLastElement = lastElement.substring(0, lastElement.lastIndexOf(".class"));
    pathFromClassFolder.add(newLastElement);
    StringBuffer classPath = new StringBuffer();
    for (int i = 0; i < pathFromClassFolder.size(); i++) {
      String onePathElement = pathFromClassFolder.get(i);
      if (i != 0) {
        classPath.append(".");
      }
      classPath.append(onePathElement);
    }
    System.out.println(File.pathSeparator);
    return classPath.toString();
  }
}
