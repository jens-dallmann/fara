package core.service.exceptions;

public class CopyException extends Exception {
  private static final long serialVersionUID = 1L;

  public CopyException(String filename, Throwable thrown) {
    super("Error on copy file: " + filename, thrown);
  }
}
