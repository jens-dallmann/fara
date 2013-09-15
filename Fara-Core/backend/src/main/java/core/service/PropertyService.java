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
  public static final String FILE_DIRECTORY = "filedirectory";
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
    FileWriter out = null;
    try {
      out = new FileWriter(new File(FILENAME));
      properties.store(out, "Stored Properties");
    } catch (FileNotFoundException e) {
      throw new PropertyException("Storing Properties failed");
    } catch (IOException e) {
      throw new PropertyException("Storing Properties failed");
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          throw new PropertyException("Closing Property file " + FILENAME + " after saving the property file went wrong.");
        }
      }
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
      try {
        File file = new File(FILENAME);
        boolean isCreated = file.createNewFile();
        if (isCreated) {
          propertyFileStream = new FileInputStream(FILENAME);
          properties.load(propertyFileStream);
          propertyFileStream.close();
        }
      } catch (IOException e1) {
        throw new PropertyException("Properties file can not be loaded");
      }
      finally {
        if (propertyFileStream != null) {
          try {
            propertyFileStream.close();
          } catch (IOException e1) {
            throw new PropertyException("Properties file can not be loaded");
          }
        }
      }
    } catch (IOException e) {
      throw new PropertyException("Properties file can not be loaded");
    } finally {
      if (propertyFileStream != null) {
        try {
          propertyFileStream.close();
        } catch (IOException e) {
          throw new PropertyException("Properties file can not be loaded");
        }
      }
    }

  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }
}
