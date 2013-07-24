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
	
	/**
	 * @parameter 
	 */
	private String explicitDefinedOutputDirectory;
	
	public void execute() throws MojoExecutionException, MojoFailureException {
		
		System.out.println(outputDirectory);
		System.out.println(explicitDefinedOutputDirectory);
		File outputDirectoryFile = new File(outputDirectory);

		if (outputDirectoryFile.exists()) {
			List<Class<?>> allClasses = loadCompiledClasses(new File(
					outputDirectory), new ArrayList<Class<?>>());
			DocPathNamePair pair = buildDocGenDescription();
			DocGeneratorService docGeneratorService = new DocGeneratorService();
			docGeneratorService.generateDocsByClasses(pair, allClasses);
		}
	}

	public DocPathNamePair buildDocGenDescription() {
		String usedResultDirectory = targetDirectory;
		if(explicitDefinedOutputDirectory != null) {
			usedResultDirectory = explicitDefinedOutputDirectory;
		}
		File directoryFile = new File(usedResultDirectory);
		if(!directoryFile.exists()) {
			directoryFile.mkdir();
		}
		String resultFilePath = directoryFile.getAbsolutePath()
				+ File.separator + artifactId + "FitCommandDocs";
		
		DocPathNamePair pair = new DocPathNamePair(outputDirectory,
				resultFilePath);
		return pair;
	}

	private List<Class<?>> loadCompiledClasses(File outputDirectory,
			List<Class<?>> allClasses) throws MojoExecutionException {

		for (File oneFile : outputDirectory.listFiles()) {
			if (oneFile.isDirectory()) {
				loadCompiledClasses(oneFile, allClasses);
			} else {
				if (oneFile.getAbsolutePath().contains(".class")) {
					String canonicalPath = null; 
					try {
						canonicalPath = oneFile.getCanonicalPath();
					} catch (IOException e1) {
						getLog().warn("Skipped Class "+oneFile.getAbsolutePath() +": Can not read it from file");
					}
					int indexOf = canonicalPath.indexOf("\\classes\\");
					String classPath = canonicalPath.substring(indexOf
							+ "\\classes\\".length());
					classPath = classPath.replaceAll("\\\\", ".");
					classPath = classPath.replaceAll("\\.class", "");
					try {
						Class<?> loadClass = this.getClass().getClassLoader().loadClass(classPath);
						allClasses.add(loadClass);
						getLog().info("Successfully loaded class: "+loadClass.getName());
					} catch (ClassNotFoundException e) {
						getLog().warn("Skipped Class "+oneFile.getAbsolutePath() +": Can not load it from class loader. The class to load by ClassLoader is: "+classPath);
					}
				}
			}
		}
		return allClasses;
	}

}
