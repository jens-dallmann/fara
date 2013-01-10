package docGenerator.processors.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import docGenerator.FileClassLoader;
import docGenerator.model.FitCommandDoc;
import docGenerator.processors.FileProcessor;
import fitArchitectureAdapter.annotations.FitCommand;

public class ClassFileProcessor implements FileProcessor {

	private FileClassLoader classLoader;

	public ClassFileProcessor(FileClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public List<FitCommandDoc> process(String fileToProcess) {
		List<FitCommandDoc> result = new ArrayList<FitCommandDoc>();
		Class<?> loadedClass = loadClass(fileToProcess);
		if (loadedClass != null) {
			result = collectFitCommands(loadedClass);
		}
		return result;
	}

	private List<FitCommandDoc> collectFitCommands(Class<?> loadedClass) {
		List<FitCommandDoc> commands = new ArrayList<FitCommandDoc>();
		Method[] methods = loadedClass.getMethods();
		for (Method method : methods) {
			if (isFitCommand(method)) {
				FitCommandDoc commandDoc = buildCommandDoc(method);
				commands.add(commandDoc);
			}
		}
		return commands;
	}

	private FitCommandDoc buildCommandDoc(Method method) {
		FitCommandDoc doc = new FitCommandDoc();
		doc.setCommandName(method.getName());
		List<String> commandParams = buildCommandParams(method);
		doc.setCommandParams(commandParams);
		return doc;
	}

	private List<String> buildCommandParams(Method method) {
		List<String> commandParams = new ArrayList<String>();
		FitCommand parameters = method.getAnnotation(FitCommand.class);
		if (parameters != null) {
			for (String parameter : parameters.value()) {
				commandParams.add(parameter);
			}
		}
		return commandParams;
	}

	private boolean isFitCommand(Method method) {
		FitCommand annotation = method.getAnnotation(FitCommand.class);
		return annotation != null;
	}

	private Class<?> loadClass(String fileToProcess) {
		Class<?> loadClass = null;
		try {
			loadClass = classLoader.loadClass(fileToProcess);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return loadClass;
	}
}
