import core.ClassLoaderUtils;
import core.LoadClassCrawlerAction;
import core.ProcessListener;
import core.service.FitIOService;
import core.service.PropertyService;
import core.service.exceptions.CreateDirectoryException;
import core.service.exceptions.FitIOException;
import directoryCrawler.DirectoryCrawler;
import directoryCrawler.NoActionsToExecuteException;
import fit.Parse;
import fit.exception.FitParseException;
import fitArchitectureAdapter.AbstractActionFixtureAggregator;
import fitArchitectureAdapter.container.InstanceMethodPair;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

import javax.xml.bind.PropertyException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @threadSafe false
 * @goal test
 * @phase integration-test
 * @requiresDependencyResolution test
 * @requiresDependencyCollection test
 */
public class FaraTestRunner extends AbstractMojo {
    private static final String CONSOLIDATE_RESULT_FOLDER = "consolidated_result_folder";
    private static final String TEST_DIRECTORY_FOLDER = "test_directory_folder";
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
     * The directory path were test files are stored
     * can be passed as maven parameter or set in user home .fara-test-configuration.properties with test_directory_folder = "/any/absolute/path"
     * <p/>
     * the maven parameter has a higher priority than the configuration in property file
     *
     * @parameter
     */
    private String faraTestsDirectoryPath;

    /**
     * The directory were all result files should be stored.
     * can be passed as maven parameter or set in user home .fara-test-configuration.properties with  consolidated_result_folder = "/any/absolute/path"
     * <p/>
     * the maven parameter has a higher priority than the configuration in property file
     * the default value is: /target/faraTestResults
     *
     * @parameter
     */
    private String consolidatedResultFolder;
    private String propertyFile;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        String userHome = System.getProperties().getProperty("user.home");
        propertyFile = userHome + File.separator + ".fara-test-configuration.properties";
        List<File> allTestFiles = null;
        try {
            allTestFiles = collectTestFiles();
        } catch (PropertyException e) {
            throw new MojoExecutionException(e.getMessage());
        }
        List<AbstractActionFixtureAggregator> testProcessors = scanForAbstractFixtureAggregator();
        getLog().info("Found " + testProcessors.size() + " possible test processors");
        List<Pair> pairs = buildPairs(testProcessors, allTestFiles);
        for (Pair onePair : pairs) {
            AbstractActionFixtureAggregator testProcessor = configureFixtureAggregator(onePair);
            List<File> testFiles = onePair.getTestFiles();
            for (File testFile : testFiles) {
                try {
                    FitIOService service = new FitIOService();
                    service.createResultDirectory(testFile);
                    String testFileContent = FileUtils.readFileToString(testFile);
                    Parse htmlTable = new Parse(testFileContent);
                    Parse firstProcessableRow = htmlTable.at(0, 0).more;
                    Parse processableRow = firstProcessableRow;
                    while (processableRow != null) {
                        testProcessor.doRow(processableRow);
                        processableRow = processableRow.more;
                    }
                    getLog().info("Write result file for: " + testFile.getAbsolutePath());
                    service.writeTestResult(testFile, firstProcessableRow, htmlTable.at(0, 0, 0).text());
                } catch (IOException e) {
                    getLog().warn("Can not load testfile " + testFile.getAbsolutePath() + " while try to process it");
                } catch (FitParseException e) {
                    getLog().warn("Can not parse testfile " + testFile.getAbsolutePath() + " while try to process it");
                } catch (CreateDirectoryException e) {
                    getLog().warn("Can not create result directory for file: " + testFile.getAbsolutePath());
                } catch (FitIOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            consolidateResultFiles();
        } catch (NoActionsToExecuteException e) {
            e.printStackTrace();
        }
    }

    private void consolidateResultFiles() throws NoActionsToExecuteException {
        DirectoryCrawler crawler = new DirectoryCrawler();
        File testDirectoryFolder = new File(faraTestsDirectoryPath);
        File resultDirectory = readResultDirectory();
        CopyResultFileCrawlerAction action = new CopyResultFileCrawlerAction(resultDirectory);
        crawler.crawlDirectory(testDirectoryFolder, null, action, true);
    }

    private File readResultDirectory() {
        if (consolidatedResultFolder == null) {
            getLog().info("no folder for test results set in maven property. Try to use property file");
            String targetDirectory = getTargetDirectory();
            File defaultResultFolder = new File(targetDirectory + File.separator + "faraTestResults" + File.separator);
            File file = new File(propertyFile);
            if (file.exists()) {
                try {
                    PropertyService service = new PropertyService(propertyFile);
                    String consolidateResultFolder = service.getProperty(CONSOLIDATE_RESULT_FOLDER);
                    if (consolidateResultFolder != null) {
                        getLog().info("Found result folder in property file. Will use: " + consolidateResultFolder);
                        return new File(consolidateResultFolder);
                    }
                } catch (PropertyException e) {
                    getLog().info("Failed to read Property for result folder. Will use default result folder for consolidation: " + defaultResultFolder.getAbsolutePath());
                    return defaultResultFolder;
                }
                getLog().info("Failed to read Property for result folder. Will use default result folder for consolidation: " + defaultResultFolder.getAbsolutePath());
                return defaultResultFolder;
            } else {
                getLog().info("No property file exist in user.home. Will use default result folder for consolidation: " + defaultResultFolder.getAbsolutePath());
                return defaultResultFolder;
            }
        } else {
            getLog().info("Use maven property for test results: " + consolidatedResultFolder);
            return new File(consolidatedResultFolder);
        }
    }

    private String getTargetDirectory() {
        String[] directoryPath = outputDirectory.split(File.separator);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < directoryPath.length - 1; i++) {
            builder.append(directoryPath[i]).append(File.separator);
        }
        return builder.toString();
    }

    private AbstractActionFixtureAggregator configureFixtureAggregator(Pair onePair) {
        AbstractActionFixtureAggregator testProcessor = onePair.getFixtureAggregator();
        testProcessor.registerProcessListener(new ProcessListener() {
            @Override
            public void publishResult(String state, String message) {
            }

            @Override
            public void addedCommandToMap(InstanceMethodPair pair, String commandName) {
            }
        });
        return testProcessor;
    }

    private List<Pair> buildPairs(List<AbstractActionFixtureAggregator> testProcessors, List<File> testFiles) {
        List<Pair> pairs = new ArrayList<Pair>();
        for (AbstractActionFixtureAggregator oneProcessor : testProcessors) {
            List<File> filtered = filterTestfiles(testFiles, oneProcessor);
            getLog().info("Searching for files for Test Processor class: " + oneProcessor.getClass().getName());

            Pair pair = new Pair(filtered, oneProcessor);
            pairs.add(pair);
        }
        getLog().info("Loaded Pairs: " + pairs.size());
        for (Pair pair : pairs) {
            getLog().info("Testfiles for " + pair.getFixtureAggregator().getClass());
            List<File> aggregatorTestFiles = pair.getTestFiles();
            for (File oneTestFile : aggregatorTestFiles) {
                getLog().info(oneTestFile.getAbsolutePath());
            }
        }
        return pairs;
    }

    private List<File> filterTestfiles(List<File> testFiles, AbstractActionFixtureAggregator oneProcessor) {
        List<File> filteredCollection = new ArrayList<File>();

        for (File testFile : testFiles) {
            TestProcessorFileFilter fileFilter = new TestProcessorFileFilter(getLog(), oneProcessor);
            if (fileFilter.accept(testFile)) {
                filteredCollection.add(testFile);
            }
        }
        return filteredCollection;
    }

    private List<AbstractActionFixtureAggregator> scanForAbstractFixtureAggregator() throws MojoExecutionException {
        List<AbstractActionFixtureAggregator> testProcessors = new ArrayList<AbstractActionFixtureAggregator>();
        ClassLoader classLoader = ClassLoaderUtils.buildClassLoader(getLog(), this.getClass().getClassLoader(), compileClasspathElements);
        List<Class<?>> classes = ClassLoaderUtils.loadClassesRecursivelyFromDirectory(new DirectoryCrawler(), classLoader, new File(outputDirectory));
        for (Class<?> possibleFixtureAggregator : classes) {
            if (AbstractActionFixtureAggregator.class.isAssignableFrom(possibleFixtureAggregator)) {
                try {
                    Object aggregator = possibleFixtureAggregator.newInstance();
                    testProcessors.add((AbstractActionFixtureAggregator) aggregator);
                    getLog().info("Successfully loaded fixture class: " + possibleFixtureAggregator);
                } catch (InstantiationException e) {
                    getLog().warn("Could not instantiate fixture aggregator: " + possibleFixtureAggregator);
                } catch (IllegalAccessException e) {
                    getLog().warn("Can not access fixture aggregator: " + possibleFixtureAggregator);
                }
            }
        }
        return testProcessors;
    }

    private List<File> collectTestFiles() throws MojoFailureException, PropertyException {
        List<File> testFiles = new ArrayList<File>();
        File sourceDirectory = createAndValidateSourceDirectory();
        getLog().info("Try to load test files from: " + sourceDirectory.getAbsolutePath());
        testFiles.addAll(collectTestFiles(sourceDirectory));
        getLog().info(testFiles.size() + " Test Files Loaded");
        return testFiles;
    }

    private File createAndValidateSourceDirectory() throws MojoFailureException, PropertyException {
        File testDirectory;
        if (faraTestsDirectoryPath != null) {
            getLog().info("Maven Property found for test directory: " + faraTestsDirectoryPath);
            testDirectory = new File(faraTestsDirectoryPath);
        } else {
            getLog().info("Try to read test directory from property file");
            testDirectory = readTestDirectoryFromProperty();
            if (testDirectory != null) {
                getLog().info("Will use test directory from Property file: " + testDirectory.getAbsolutePath());
            }
        }
        if (testDirectory == null) {
            throw new MojoFailureException("No test directory as Maven Parameter and no test directory set in property file.");
        }
        faraTestsDirectoryPath = testDirectory.getAbsolutePath();
        getLog().info("Test Directory is: " + testDirectory.getAbsolutePath());
        if (!testDirectory.exists()) {
            throw new MojoFailureException("Testdirectory \"" + testDirectory.getAbsolutePath() + " \"");
        }
        if (!testDirectory.isDirectory()) {
            throw new MojoFailureException("Given Testdirectory \"" + testDirectory.getAbsolutePath() + "\" is not a directory");
        }
        return testDirectory;
    }

    private File readTestDirectoryFromProperty() throws PropertyException {
        PropertyService propertyService = new PropertyService(propertyFile);
        String test_folder_path = propertyService.getProperty(TEST_DIRECTORY_FOLDER);
        if (test_folder_path == null) {
            return null;
        }
        return new File(test_folder_path);
    }

    private Collection<? extends File> collectTestFiles(File sourceDirectory) {
        List<File> testFiles = new ArrayList<File>();
        if (sourceDirectory.isDirectory()) {
            for (File fileInDirectory : sourceDirectory.listFiles(new TestFileFileFilter())) {
                testFiles.addAll(collectTestFiles(fileInDirectory));
            }
        } else {
            boolean isHtmlFile = sourceDirectory.getAbsolutePath().endsWith(".html");
            boolean isResultFile = sourceDirectory.getAbsolutePath().endsWith("_result.html");
            if (isHtmlFile && !isResultFile) {
                testFiles.add(sourceDirectory);
                getLog().info("Loaded Testfile: " + sourceDirectory.getAbsolutePath());
            }
        }

        return testFiles;
    }
}
