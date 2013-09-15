import fitArchitectureAdapter.AbstractActionFixtureAggregator;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Deception
 * Date: 29.07.13
 * Time: 20:12
 * To change this template use File | Settings | File Templates.
 */
public class Pair {

  private List<File> testFiles;
  private AbstractActionFixtureAggregator fixtureAggregator;

  public Pair(List<File> testFiles, AbstractActionFixtureAggregator fixtureAggregator) {
    this.testFiles = testFiles;
    this.fixtureAggregator = fixtureAggregator;
  }

  public AbstractActionFixtureAggregator getFixtureAggregator() {
    return fixtureAggregator;
  }

  public List<File> getTestFiles() {
    return testFiles;
  }
}
