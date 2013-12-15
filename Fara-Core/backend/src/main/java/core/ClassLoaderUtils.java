package core;

import directoryCrawler.DirectoryCrawler;
import directoryCrawler.NoActionsToExecuteException;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Class<?>> loadClassesRecursivelyFromDirectory(DirectoryCrawler directoryCrawler, ClassLoader classLoader, File file) {
        LoadClassCrawlerAction loadClassAction = new LoadClassCrawlerAction(classLoader);
        try {
            directoryCrawler.crawlDirectory(file, null, loadClassAction, true);
        } catch (NoActionsToExecuteException e) {
            e.printStackTrace();
        }
        return loadClassAction.getResult();
    }
}
