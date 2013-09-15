package core.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.xml.bind.PropertyException;

public class PropertyService {
  public static final String FILENAME = "settings.properties";
  public static final String ROOT_FOLDER_PATH = "rootFolderPath";
  private static Properties properties;

  public PropertyService() throws PropertyException {
    loadProperties();
  }

  public void saveInProperty(String propertyName, String rootFolderPath)
          throws PropertyException {
    properties.setProperty(propertyName, rootFolderPath);
    savePropertyFile();
  }

  private void savePropertyFile() throws PropertyException {
    try {
      FileWriter out = new FileWriter(new File(FILENAME));
      properties.store(out, "Stored Properties");
      out.close();
    } catch (FileNotFoundException e) {
      throw new PropertyException("Storing Properties failed");
    } catch (IOException e) {
      throw new PropertyException("Storing Properties failed");
    }
  }

  private void loadProperties() throws PropertyException {
    properties = new Properties();
    FileInputStream propertyFileStream = null;
    try {
      propertyFileStream = new FileInputStream(FILENAME);
      properties.load(propertyFileStream);
      propertyFileStream.close();
    } catch (FileNotFoundException e) {
      File file = new File(FILENAME);
      try {
        file.createNewFile();
        properties.load(propertyFileStream);
        propertyFileStream.close();
      } catch (IOException e1) {
        throw new PropertyException("Properties file can not be loaded");
      }

    } catch (IOException e) {
      throw new PropertyException("Properties file can not be loaded");
    }

  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }
}
