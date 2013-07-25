package core.service;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class RessourceService {
	public String loadRessourceFilePath(String filename) throws URISyntaxException{
		return loadRessourceFile(filename).getAbsolutePath();
	}
	public File loadRessourceFile(String filename) throws URISyntaxException{
		ClassLoader classLoader = RessourceService.class
				.getClassLoader();
		URL resourceUrl = classLoader.getResource(filename);
		File resourceFile = new File(resourceUrl.toURI());
		return resourceFile;
	}
}
