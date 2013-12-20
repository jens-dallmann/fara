package core.service;

import core.service.exceptions.CopyException;
import core.service.exceptions.CreateFileException;
import core.service.exceptions.WriteFileException;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Scanner;


public class FileService {

  public static final String ENCODING = "UTF-8";

  public File createFileIfNotExist(File file) throws CreateFileException {
    boolean isCreated = false;
    try {
      if (!file.exists()) {
        isCreated = file.createNewFile();
      } else {
        isCreated = true;
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new CreateFileException(file.getAbsolutePath(), e);
    }
    if (!isCreated) {
      throw new CreateFileException(file.getAbsolutePath());
    }
    return file;
  }

  public File createFileIfNotExist(String filepath) throws CreateFileException {
    File file = new File(filepath);
    return createFileIfNotExist(file);
  }

  public boolean writeToFile(File file, String content) throws WriteFileException {
    Writer out = null;
    try {
      out = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(file), ENCODING));
      boolean result = writeToFile(out, content);
      out.close();
      return result;
    } catch (IOException e) {
      throw new WriteFileException(file.getAbsolutePath(), e);
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          throw new WriteFileException(file.getAbsolutePath(), e);
        }
      }
    }
  }

  private boolean writeToFile(Writer fileWriter, String content)
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
    content = new Scanner(file, ENCODING).useDelimiter("\\Z").next();
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
