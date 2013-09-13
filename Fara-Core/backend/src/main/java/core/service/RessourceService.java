package core.service;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class RessourceService {
	public String loadRessourceFilePath(String filename) throws URISyntaxException{
		return loadRessourceFile(filename).getAbsolutePath();
	}
	public File loadRessourceFile(String filename) throws URISyntaxException{
        ClassLoader classLoader = this.getClass().getClassLoader();
		String resourcePath = classLoader.getResource(filename).getPath();

		File resourceFile = new File(resourcePath);
		return resourceFile;
	}
}
