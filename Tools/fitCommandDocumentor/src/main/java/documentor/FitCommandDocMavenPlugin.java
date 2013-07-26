package documentor;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
     * Directory where compiled classes will be
     *
     * @parameter expression="${project.compileClasspathElements}"
     * @required
     * @readonly
     */
    private List<String> compileClasspathElements;

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
    private URLClassLoader loader;

    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            this.loader = new URLClassLoader(buildUrls(), this.getClass().getClassLoader());
        } catch (MalformedURLException e) {
            throw new MojoExecutionException("URL malformed: " + e.getMessage());
        }
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

    private URL[] buildUrls() throws MalformedURLException {
        List<URL> urls = new ArrayList<URL>();
        for (Object object : compileClasspathElements) {
            String path = (String) object;
            urls.add(new File(path).toURI().toURL());
        }
        return urls.toArray(new URL[]{});
    }

    private DocPathNamePair buildDocGenDescription() {
        String usedResultDirectory = targetDirectory;
        if (explicitDefinedOutputDirectory != null) {
            usedResultDirectory = explicitDefinedOutputDirectory;
        }
        File directoryFile = new File(usedResultDirectory);
        if (!directoryFile.exists()) {
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
                        getLog().warn("Skipped Class " + oneFile.getAbsolutePath() + ": Can not read it from file");
                    }
                    String classPath = buildClassPath(oneFile.getAbsolutePath());
                    try {
                        ClassLoader classLoader = this.getClass().getClassLoader();
                        Class<?> loadClass = loader.loadClass(classPath);

                        allClasses.add(loadClass);
                        getLog().info("Successfully loaded class: " + loadClass.getName());
                    } catch (ClassNotFoundException e) {
                        getLog().warn("Skipped Class " + oneFile.getAbsolutePath() + ": Can not load it from class loader. The class to load by ClassLoader is: " + classPath);
                    }
                }
            }
        }
        return allClasses;
    }

    private String buildClassPath(String absolutePath) {
        String pattern = Pattern.quote(System.getProperty("file.separator"));
        String[] splittedPath = absolutePath.split(pattern);
        List<String> pathFromClassFolder = new ArrayList<String>();
        boolean isClassesFolderPassed = false;
        for (String onePathElement : splittedPath) {
            if (isClassesFolderPassed) {
                pathFromClassFolder.add(onePathElement);
            }
            if (onePathElement.equals("classes")) {
                isClassesFolderPassed = true;
            }
        }
        String lastElement = pathFromClassFolder.get(pathFromClassFolder.size() - 1);
        pathFromClassFolder.remove(pathFromClassFolder.size() - 1);
        String newLastElement = lastElement.substring(0, lastElement.lastIndexOf(".class"));
        pathFromClassFolder.add(newLastElement);
        StringBuffer classPath = new StringBuffer();
        for (int i = 0; i < pathFromClassFolder.size(); i++) {
            String onePathElement = pathFromClassFolder.get(i);
            if (i != 0) {
                classPath.append(".");
            }
            classPath.append(onePathElement);
        }
        System.out.println(File.pathSeparator);
        return classPath.toString();
    }
}
