package docGenerator.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import docGenerator.FileClassLoader;
import docGenerator.HTMLBuilder;
import docGenerator.model.DocPathNamePair;
import docGenerator.model.FitCommandDoc;
import docGenerator.processors.impl.ClassFileProcessor;
import docGenerator.processors.impl.FolderFileProcessor;

public class DocGeneratorService {
	public void generateDocs(List<DocPathNamePair> pairs ) {
		for(DocPathNamePair pair: pairs) {
			generateDocs(pair.getPath(), pair.getHtmlFileName());
		}
	}
	
	public void generateDocsByClasses(DocPathNamePair targetDescription, List<Class<?>> classes) {
		List<FitCommandDoc> pairs = new ArrayList<FitCommandDoc>();
		for(Class<?> clazz: classes) {
			ClassFileProcessor classFileProcessor = new ClassFileProcessor();
			pairs.addAll(classFileProcessor.process(clazz));
		}
		generateDocs(targetDescription.getHtmlFileName(), pairs);
	}
	
	private void generateDocs(String path, String htmlFileName) {
		List<FitCommandDoc> process = collectCommandsForPath(path);
		generateDocs(htmlFileName, process);
	}

	public void generateDocs(String htmlFileName, List<FitCommandDoc> process) {
		Collections.sort(process);
		String buildHtml = new HTMLBuilder().build(process);
		try {
			new FileService().writeToFileCreateIfNotExist(htmlFileName+".html",
					buildHtml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static List<FitCommandDoc> collectCommandsForPath(String path) {
		FileClassLoader classLoader = new FileClassLoader(path);
		FolderFileProcessor processor = new FolderFileProcessor(
				new ClassFileProcessor(classLoader), path);
		List<FitCommandDoc> process = processor.process("");
		return process;
	}
}
