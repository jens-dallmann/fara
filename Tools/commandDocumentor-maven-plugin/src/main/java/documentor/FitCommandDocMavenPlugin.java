package documentor;

import core.ClassLoaderUtils;
import directoryCrawler.DirectoryCrawler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;
import java.util.List;

@Mojo(name = "install", defaultPhase = LifecyclePhase.PROCESS_CLASSES, requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class FitCommandDocMavenPlugin extends AbstractMojo {

  /**
   * Directory where compiled classes will be
   */
  @Parameter(readonly = true, required = true, defaultValue = "${project.build.outputDirectory}")
  private String outputDirectory;
  /**
   * Directory where compiled classes will be
   */
  @Parameter(readonly=true, required=true, defaultValue="${project.compileClasspathElements}")
  private List<String> compileClasspathElements;

  /**
   * Artifact ID of compile project
   */
  @Parameter(readonly=true, required=true, defaultValue="${project.artifactId}")
  private String artifactId;

  /**
   * The projects target directory
   */
  @Parameter(readonly = true, required = true, defaultValue = "${project.build.directory}")
  private String targetDirectory;

  @Parameter
  private String explicitDefinedOutputDirectory;

  private ClassLoader loader;

  public void execute() throws MojoExecutionException, MojoFailureException {
    this.loader = ClassLoaderUtils.buildClassLoader(getLog(), this.getClass().getClassLoader(), compileClasspathElements);

    getLog().info("Loading classes from directory: " + outputDirectory);
    File outputDirectoryFile = new File(outputDirectory);

    if (outputDirectoryFile.exists()) {
      List<Class<?>> allClasses = ClassLoaderUtils.loadClassesRecursivelyFromDirectory(new DirectoryCrawler(), loader, outputDirectoryFile);
      DocPathNamePair pair = buildDocGenDescription();
      DocGeneratorService docGeneratorService = new DocGeneratorService();
      docGeneratorService.generateDocsByClasses(pair, allClasses);
    }
  }


  private DocPathNamePair buildDocGenDescription() {
    String usedResultDirectory = targetDirectory;
    if (explicitDefinedOutputDirectory != null) {
      usedResultDirectory = explicitDefinedOutputDirectory;
    }
    String directoryBySystemProperty = System.getProperty("explicitDefinedOutputDirectory");
    if (directoryBySystemProperty != null) {
      usedResultDirectory= directoryBySystemProperty;
    }
    getLog().info("Using " + usedResultDirectory + " as ouput directory for documentation");

    File resultDirectory = new File(usedResultDirectory);
    if(!resultDirectory.exists()) {
      getLog().info("Creating result directory: "+resultDirectory.getAbsolutePath());
      resultDirectory.mkdir();
    }
    File directoryFile = new File(resultDirectory, "FitCommands");
    getLog().info("create result directory: " + directoryFile.getPath());
    if (!directoryFile.exists()) {
      directoryFile.mkdir();
    }
    String resultFilePath = directoryFile.getAbsolutePath()
            + File.separator + artifactId + "FitCommandDocs";
    getLog().info("result file path is: " + resultFilePath);
    DocPathNamePair pair = new DocPathNamePair(resultFilePath);
    return pair;
  }
}
