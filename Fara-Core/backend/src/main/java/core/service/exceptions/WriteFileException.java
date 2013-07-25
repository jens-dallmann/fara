package core.service.exceptions;

import java.io.IOException;

public class WriteFileException extends Exception {
	private static final long serialVersionUID = -5554700764816778315L;

	public WriteFileException(String absolutePath, IOException e) {
		super("Error on writing file: "+ absolutePath, e);
	}
}
