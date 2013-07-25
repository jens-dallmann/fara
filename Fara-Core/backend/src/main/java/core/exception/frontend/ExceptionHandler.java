package core.exception.frontend;

import java.awt.Window;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

	private static Window parent;

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		handleException(t.getName(), e);
	}

	public void handle(Throwable thrown) {
		handleException(Thread.currentThread().getName(), thrown);
	}

	private void handleException(String name, Throwable thrown) {
		new ExceptionWindowController(parent, thrown, ExceptionLevel.ERROR, new ApplicationExceptionAreaFiller());
	}

	public static void setParent(Window parent) {
		ExceptionHandler.parent = parent;
	}
}
