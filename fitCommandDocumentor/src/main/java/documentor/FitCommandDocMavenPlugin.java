package documentor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import docGenerator.model.DocPathNamePair;
import docGenerator.services.DocGeneratorService;

/**
 * @goal install
 * @phase process-classes
 * @requiresDependencyResolution compile+runtime
 */
public class FitCommandDocMavenPlugin extends AbstractMojo {

	/**
	 * Directory where compiled classes will be
	 * 
	 * @parameter expression="${project.build.outputDirectory}"
	 * @required
	 * @readonly
	 */
	private String outputDirectory;
	
	/**
	 * Target Directory
	 * 
	 * @parameter expression="${project.build.directory}"
	 * @required
	 * @readonly
	 */
	private String targetDirectory;
	
	/**
	 * Artifact ID of compile project
	 * 
	 * @parameter expression="${project.artifactId}"
	 * @required
	 * @readonly
	 */
	private String artifactId;
	
	public void execute() throws MojoExecutionException, MojoFailureException {

		List<Class<?>> allClasses = loadCompiledClasses(new File(outputDirectory), new ArrayList<Class<?>>());
		String directory = targetDirectory;
		File directoryFile = new File(directory);
		
		String resultFilePath = directoryFile.getAbsolutePath()
				+ File.separator + artifactId
				+ "FitCommandDocs";
		DocPathNamePair pair = new DocPathNamePair(outputDirectory,
				resultFilePath);
		DocGeneratorService docGeneratorService = new DocGeneratorService();
		docGeneratorService.generateDocsByClasses(pair, allClasses);
	}

	private List<Class<?>> loadCompiledClasses(File outputDirectory,
			List<Class<?>> allClasses) throws MojoExecutionException {
		ClassLoader classLoader = FitCommandDocMavenPlugin.class.getClassLoader();
		for (File oneFile : outputDirectory.listFiles()) {
			if (oneFile.isDirectory()) {
				loadCompiledClasses(oneFile, allClasses);
			} else {
				if (oneFile.getAbsolutePath().contains(".class")) {
					try {
						String canonicalPath = oneFile.getCanonicalPath();
						int indexOf = canonicalPath.indexOf("\\classes\\");
						String substring = canonicalPath.substring(indexOf
								+ "\\classes\\".length());
						substring = substring.replaceAll("\\\\", ".");
						substring = substring.replaceAll("\\.class", "");
						Class<?> loadClass = classLoader.loadClass(substring);
						allClasses.add(loadClass);
					} catch (ClassNotFoundException e) {
						throw new MojoExecutionException("Can not load class: "+oneFile.getAbsolutePath());
					} catch (IOException e) {
						throw new MojoExecutionException("Can not load class: "+oneFile.getAbsolutePath());
					}
				}
			}
		}
		return allClasses;
	}

}
