package core.service.exceptions;

public class FitIOException extends Exception {

  private static final long serialVersionUID = 1L;


  public FitIOException(String string, Exception e) {
    super(string, e);
  }

}
