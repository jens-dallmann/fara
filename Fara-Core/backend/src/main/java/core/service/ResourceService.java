package core.service;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourceService {
  public String loadResourceFilePath(Class<?> clazz, String filename) throws URISyntaxException {
    return loadResourceFile(clazz, filename).getAbsolutePath();
  }

  public File loadResourceFile(Class<?> clazz, String filename) throws URISyntaxException {
    ClassLoader loader = clazz.getClassLoader();
    URL url = loader.getResource(filename);
    if (url == null) {
      url = ResourceService.class.getResource("/" + filename);
    }

    String resourcePath = url.getPath();

    File resourceFile = new File(resourcePath);
    return resourceFile;
  }
}
