package docGenerator.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileService {
  public File createFileIfNotExist(File file) {
    try {
      if (!file.exists()) {
        file.createNewFile();
      }
    } catch (IOException e) {
    }
    return file;
  }

  public File createFileIfNotExist(String filepath) {
    File file = new File(filepath);
    return createFileIfNotExist(file);
  }

  public boolean writeToFile(File file, String content)
          throws IOException {
    FileWriter fileWriter = new FileWriter(file, false);
    return writeToFile(fileWriter, content);
  }

  public boolean writeToFile(FileWriter fileWriter, String content)
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
        }
      }
    }
    return true;

  }

  public boolean writeToFile(String filepath, String content)
          throws IOException {
    File file = new File(filepath);
    return writeToFile(file, content);
  }

  public boolean writeToFileCreateIfNotExist(String filepath,
                                             String content) throws IOException {
    createFileIfNotExist(filepath);
    return writeToFile(filepath, content);
  }
}
