package testEditor.frontend;

import java.io.File;

import core.exception.frontend.ApplicationExceptionAreaFiller;
import core.exception.frontend.ExceptionLevel;
import core.exception.frontend.ExceptionWindowController;
import core.processableTable.ProcessService;
import core.service.FitIOService;
import core.service.exceptions.FitIOException;
import fit.Parse;

public class StartTestEditor {

	public static void main(String[] args) {
		new StartTestEditor().start(args);
	}

	public void start(String[] args) {
		Parse input = readInput(args);
		if (input != null) {
			ProcessService service = createService(input);
			new TestEditorController(service,
					input);
		}
	}

	private ProcessService createService(Parse input) {
		String fixtureName = input.at(0, 0, 0).text();
		Class<?> forName = null;
		try {
			forName = Class.forName(fixtureName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(ProcessService.class.isAssignableFrom(forName)) {
			try {
				Object newInstance = forName.newInstance();
				return (ProcessService) newInstance;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}

	private Parse readInput(String[] args) {
		if (args.length == 1) {
			FitIOService fitService = new FitIOService();
			try {
				return fitService.readTest(new File(args[0]));
			} catch (FitIOException e) {
				new ExceptionWindowController(null, e, ExceptionLevel.WARNING,
						new ApplicationExceptionAreaFiller());
			}
		}
		return null;
	}
}
