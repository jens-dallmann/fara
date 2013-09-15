package core.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

import core.service.exceptions.CopyException;
import core.service.exceptions.CreateFileException;
import core.service.exceptions.WriteFileException;


public class FileService {
  public File createFileIfNotExist(File file) throws CreateFileException {
    boolean isCreated = false;
    try {
      if (!file.exists()) {
        isCreated = file.createNewFile();

      }
    } catch (IOException e) {
      throw new CreateFileException(file.getAbsolutePath(), e);
    }
    if(!isCreated) {
      throw new CreateFileException(file.getAbsolutePath());
    }
    return file;
  }

  public File createFileIfNotExist(String filepath) throws CreateFileException {
    File file = new File(filepath);
    return createFileIfNotExist(file);
  }

  public boolean writeToFile(File file, String content) throws WriteFileException {
    try {
      FileWriter fileWriter = new FileWriter(file, false);
      boolean result = writeToFile(fileWriter, content);
      fileWriter.close();
      return result;
    } catch (IOException e) {
      throw new WriteFileException(file.getAbsolutePath(), e);
    }
  }

  private boolean writeToFile(FileWriter fileWriter, String content)
          throws IOException {
    try {
      fileWriter.write(content);
    } catch (IOException ioex) {
      throw ioex;
    } finally {
      if (fileWriter != null) {
        try {
          fileWriter.close();
        } catch (IOException ioex2) {
          throw ioex2;
        }
      }
    }
    return true;

  }

  public boolean writeToFile(String filepath, String content) throws WriteFileException {
    File file = new File(filepath);
    return writeToFile(file, content);
  }

  public boolean writeToFileCreateIfNotExist(String filepath, String content)
          throws CreateFileException, WriteFileException {
    createFileIfNotExist(filepath);
    return writeToFile(filepath, content);
  }

  public String readFile(File file) throws FileNotFoundException {
    String content = "";
    content = new Scanner(file).useDelimiter("\\Z").next();
    return content;
  }

  public void copyResourceToFile(String resourceFile, String targetFile) throws CreateFileException, FileNotFoundException, CopyException {
    File target = new File(targetFile);
    createFileIfNotExist(target);
    copyResourceToFile(resourceFile, target);
  }

  private void copyResourceToFile(String resourceFile, File target) throws FileNotFoundException, CopyException {
    InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(resourceFile);
    OutputStream outputStream = null;
    outputStream = new FileOutputStream(target);
    try {
      IOUtils.copy(resourceAsStream, outputStream);
      outputStream.close();
      resourceAsStream.close();
    } catch (IOException e) {
      throw new CopyException(resourceFile, e);
    }
  }
}
