package docGenerator.processors;

import java.util.List;

import docGenerator.model.FitCommandDoc;

public interface FileProcessor {
	public List<FitCommandDoc> process(String fileToProcess);
}
