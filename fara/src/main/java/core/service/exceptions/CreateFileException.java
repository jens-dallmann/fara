package core.service.exceptions;

public class CreateFileException extends Exception{
	private static final long serialVersionUID = 1L;

	public CreateFileException(String filename, Throwable throwable) {
		super("Error on creating File: "+filename, throwable);
	}
}
