package core.service;

import javax.xml.bind.PropertyException;
import java.io.*;
import java.util.Properties;

public class PropertyService {
  public static final String DEFAULT_FILENAME = "settings.properties";
  public static final String ROOT_FOLDER_PATH = "rootFolderPath";
  private static final String ENCODING = "UTF-8";
  private static Properties properties;
  private String propertyFilePath;

  public PropertyService() throws PropertyException {
    this(DEFAULT_FILENAME);
  }

  public PropertyService(String propertyFilePath) throws PropertyException {
    this.propertyFilePath = propertyFilePath;
    loadProperties();
  }
  public void saveInProperty(String propertyName, String rootFolderPath)
          throws PropertyException {
    properties.setProperty(propertyName, rootFolderPath);
    savePropertyFile();
  }

  private void savePropertyFile() throws PropertyException {
    Writer out = null;
    try {
      out = new BufferedWriter(new OutputStreamWriter(
          new FileOutputStream(propertyFilePath), ENCODING));
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
          throw new PropertyException("Closing Property file " + propertyFilePath + " after saving the property file went wrong.");
        }
      }
    }
  }

  private void loadProperties() throws PropertyException {
    properties = new Properties();
    FileInputStream propertyFileStream = null;
    try {
      propertyFileStream = new FileInputStream(propertyFilePath);
      properties.load(propertyFileStream);
      propertyFileStream.close();
    } catch (FileNotFoundException e) {
      try {
        File file = new File(propertyFilePath);
        boolean isCreated = file.createNewFile();
        if (isCreated) {
          propertyFileStream = new FileInputStream(propertyFilePath);
          properties.load(propertyFileStream);
          propertyFileStream.close();
        }
      } catch (IOException e1) {
        throw new PropertyException("Properties file can not be loaded");
      } finally {
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
