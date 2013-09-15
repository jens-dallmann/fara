package core.service.exceptions;

public class CreateDirectoryException extends Exception {

  public CreateDirectoryException(String filename) {
    super("Directory can not be created: " + filename);
  }
}
