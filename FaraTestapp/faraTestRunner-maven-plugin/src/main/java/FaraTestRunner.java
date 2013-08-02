import core.ClassLoaderUtils;
import core.ProcessResultListener;
import fit.Parse;
import fit.exception.FitParseException;
import fitArchitectureAdapter.AbstractActionFixtureAggregator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.mockito.cglib.core.Predicate;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.cglib.core.CollectionUtils.filter;

/**
 * Created with IntelliJ IDEA.
 * User: Deception
 * Date: 26.07.13
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 *
 * @goal test
 * @phase integration-test
 * @requiresDependencyResolution compile+runtime
 */
public class FaraTestRunner extends AbstractMojo {
    /**
     * Directory where compiled classes will be
     *
     * @parameter expression="${project.compileClasspathElements}"
     * @required
     * @readonly
     */
    private List<String> compileClasspathElements;
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
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * @parameter
     */
    private String faraTestsDirectoryPath;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        List<Resource> resources = project.getResources();
        ClassLoader classLoader = ClassLoaderUtils.addResourceFilesToClasspath(resources, Thread.currentThread().getContextClassLoader());

        List<File> allTestFiles = collectTestFiles();
        List<AbstractActionFixtureAggregator> testProcessors = scanForAbstractFixtureAggregator();
        getLog().info("Found "+testProcessors.size()+" possible test processors");
        List<Pair> pairs = buildPairs(testProcessors, allTestFiles);
        for(Pair onePair: pairs) {
            AbstractActionFixtureAggregator testProcessor = configureFixtureAggregator(onePair);
            List<File> testFiles = onePair.getTestFiles();
            for(File testFile: testFiles) {
                try {
                    String testFileContent = FileUtils.readFileToString(testFile);
                    Parse htmlTable = new Parse(testFileContent);
                    Parse firstProcessableRow = htmlTable.at(0,0).more;
                    Parse processableRow = firstProcessableRow;
                    while(processableRow != null) {
                        testProcessor.doRow(processableRow);
                        processableRow = processableRow.more;
                    }
                } catch (IOException e) {
                    getLog().warn("Can not load testfile "+testFile.getAbsolutePath()+" while try to process it");
                } catch (FitParseException e) {
                    getLog().warn("Can not parse testfile "+testFile.getAbsolutePath()+" while try to process it");
                }
            }
        }

    }

    private AbstractActionFixtureAggregator configureFixtureAggregator(Pair onePair) {
        AbstractActionFixtureAggregator testProcessor = onePair.getFixtureAggregator();
        testProcessor.registerResultListener(new ProcessResultListener() {
            @Override
            public void publishResult(String state, String message) {
                getLog().info(state);
            }
        });
        return testProcessor;
    }

    private List<Pair> buildPairs(List<AbstractActionFixtureAggregator> testProcessors, List<File> testFiles) {
        List<Pair> pairs= new ArrayList<Pair>();
        for(AbstractActionFixtureAggregator oneProcessor: testProcessors) {
            List<File> filtered = filterTestfiles(testFiles,oneProcessor);
            getLog().info("Searching for files for Test Processor class: "+oneProcessor.getClass().getName());

            Pair pair = new Pair(filtered, oneProcessor);
            pairs.add(pair);
        }
        getLog().info("Loaded Pairs: "+pairs.size());
        for(Pair pair: pairs) {
            getLog().info("Testfiles for "+pair.getFixtureAggregator().getClass());
            List<File> aggregatorTestFiles = pair.getTestFiles();
            for(File oneTestFile: aggregatorTestFiles) {
                getLog().info(oneTestFile.getAbsolutePath());
            }
        }
        return pairs;
    }

    private List<File> filterTestfiles(List<File> testFiles, AbstractActionFixtureAggregator oneProcessor) {
        List<File> filteredCollection = new ArrayList<File>();

        for(File testFile: testFiles) {
            TestProcessorFileFilter fileFilter = new TestProcessorFileFilter(getLog(),oneProcessor);
            if(fileFilter.accept(testFile)) {
                filteredCollection.add(testFile);
            }
        }
        return filteredCollection;
    }

    private List<AbstractActionFixtureAggregator> scanForAbstractFixtureAggregator() throws MojoExecutionException {
        List<AbstractActionFixtureAggregator> testProcessors = new ArrayList<AbstractActionFixtureAggregator>();
        ClassLoader classLoader = ClassLoaderUtils.buildClassLoader(getLog(), this.getClass().getClassLoader(), compileClasspathElements);
        List<Class<?>> classes = ClassLoaderUtils.loadClassesRecursivelyFromDirectory(classLoader, getLog(), new File(outputDirectory), new ArrayList<Class<?>>());
        for(Class<?> possibleFixtureAggregator: classes) {
            if(AbstractActionFixtureAggregator.class.isAssignableFrom(possibleFixtureAggregator)){
                try {
                    Object aggregator = possibleFixtureAggregator.newInstance();
                    testProcessors.add((AbstractActionFixtureAggregator) aggregator);
                    getLog().info("Successfully loaded fixture class: "+possibleFixtureAggregator);
                } catch (InstantiationException e) {
                    getLog().warn("Could not instantiate fixture aggregator: " + possibleFixtureAggregator);
                } catch (IllegalAccessException e) {
                    getLog().warn("Can not access fixture aggregator: " + possibleFixtureAggregator);
                }
            }
        }
        return testProcessors;
    }

    private List<File> collectTestFiles() throws MojoFailureException {
        List<File> testFiles = new ArrayList<File>();
        File sourceDirectory = createAndValidateSourceDirectory();
        getLog().info("Try to load test files from: " + sourceDirectory.getAbsolutePath());
        testFiles.addAll(collectTestFiles(sourceDirectory));
        getLog().info(testFiles.size() + " Test Files Loaded");
        return testFiles;
    }

    private File createAndValidateSourceDirectory() throws MojoFailureException {
        File sourceDirectory = new File(faraTestsDirectoryPath);
        getLog().info("Test Directory is: "+sourceDirectory.getAbsolutePath());
        if(!sourceDirectory.exists()) {
            throw new MojoFailureException("Testdirectory \""+sourceDirectory.getAbsolutePath()+" \"");
        }
        if (!sourceDirectory.isDirectory()) {
            throw new MojoFailureException("Given Testdirectory \""+sourceDirectory.getAbsolutePath()+"\" is not a directory");
        }
        return sourceDirectory;
    }

    private Collection<? extends File> collectTestFiles(File sourceDirectory) {
        List<File> testFiles = new ArrayList<File>();
        if (sourceDirectory.isDirectory()) {
            for (File fileInDirectory : sourceDirectory.listFiles(new TestFileFileFilter())) {
                testFiles.addAll(collectTestFiles(fileInDirectory));
            }
        } else {
            testFiles.add(sourceDirectory);
            getLog().info("Loaded Testfile: " + sourceDirectory.getAbsolutePath());
        }

        return testFiles;
    }
}
