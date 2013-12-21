import core.service.FileService;
import fit.Parse;
import fit.exception.FitParseException;
import fitArchitectureAdapter.AbstractActionFixtureAggregator;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Deception
 * Date: 29.07.13
 * Time: 19:55
 * To change this template use File | Settings | File Templates.
 */
public class TestProcessorFileFilter implements FileFilter {

  private Log logger;
  private AbstractActionFixtureAggregator testfileprocessor;

  public TestProcessorFileFilter(Log logger, AbstractActionFixtureAggregator testfileprocessor) {
    this.logger = logger;

    this.testfileprocessor = testfileprocessor;
  }

  @Override
  public boolean accept(File pathname) {
    FileService fileService = new FileService();
    try {
      String fileContent = fileService.readFile(pathname);
      Parse parse = new Parse(fileContent, new String[]{"table", "tr", "td"});
      Parse fixtureColumn = parse.at(0, 0, 0);
      String fixture = fixtureColumn.text();
      logger.info("Extracted " + fixture + " as fixture class from file: " + pathname);
      logger.info(testfileprocessor.getClass().toString());
      String testfileprocessorname = testfileprocessor.getClass().getName();
      if (testfileprocessorname.equals(fixture)) {
        return true;
      }
    } catch (IOException e) {
      logger.info("Can not find file: " + pathname);
    } catch (FitParseException e) {
      logger.info("Can not parse file: " + pathname);
    }
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
