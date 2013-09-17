package fitArchitectureAdapter;

import core.ProcessListener;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.InstanceMethodPair;
import fitArchitectureAdapter.interfaces.HasCommands;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class AbstractActionFixtureAggregatoraddCommandObjectTest {

  @Mock
  private HasCommands commandObject;

  @Mock
  private ProcessListener listener;

  private AbstractActionFixtureAggregator aggregator;

  @Before
  public void beforeTest() {
    MockitoAnnotations.initMocks(this);

    aggregator = new AbstractActionFixtureAggregator() {
      @Override
      public void addFixtureObjects() {
      }
    };
    aggregator.init();
    aggregator.registerProcessListener(listener);
  }

  @Test
  public void testAddCommandObjectNoCommand() {
    aggregator.addCommandObject(new TestClassNoCommands());
    verify(listener, times(0)).addedCommandToMap(any(InstanceMethodPair.class), anyString());

  }

  @Test
  public void testAddCommandObjectOneCommandAvailable() {
    TestClassOneCommand testClass = new TestClassOneCommand();
    aggregator.addCommandObject(testClass);
    verifyListenerCall(testClass, "oneCommand");
  }

  @Test
  public void testAddCommandObjectWithOrdinaryMethods() {
    TestClassWithOrdinaryCommand testClass = new TestClassWithOrdinaryCommand();
    aggregator.addCommandObject(testClass);
    verifyListenerCall(testClass, "annotated");
    verifyNoMoreInteractions(listener);
  }

  @Test
  public void testWithDupplicatedCommand() {
    TestClassOneCommand oneCommand = new TestClassOneCommand();
    TestClassDuplicateCommand duplicateCommand = new TestClassDuplicateCommand();

    aggregator.addCommandObject(oneCommand);
    verifyListenerCall(oneCommand, "oneCommand");
    verifyListenerCall(oneCommand, "secondCommand");
    verifyNoMoreInteractions(listener);
    aggregator.addCommandObject(duplicateCommand);

  }

  private void verifyListenerCall(HasCommands testClass, String oneCommand) {
    verify(listener, times(1)).addedCommandToMap(argThat(new InstanceMethodPairMatcher(testClass, oneCommand)), eq(oneCommand));
  }

  private class TestClassNoCommands implements HasCommands {

  }

  private class TestClassOneCommand implements HasCommands {
    @FitCommand({""})
    public void oneCommand() {

    }

    @FitCommand({""})
    public void secondCommand() {

    }

  }

  private class TestClassAnotherOneWithCommands implements HasCommands {
    @FitCommand({""})
    public void thirdCommand() {

    }
  }

  private class TestClassDuplicateCommand implements HasCommands {
    @FitCommand({""})
    public void oneCommand() {

    }
  }

  private class InstanceMethodPairMatcher extends ArgumentMatcher<InstanceMethodPair> {

    private HasCommands commandObject;

    private String methodName;

    public InstanceMethodPairMatcher(HasCommands commandObject, String methodName) {
      this.commandObject = commandObject;
      this.methodName = methodName;
    }

    @Override
    public boolean matches(Object o) {

      if (o instanceof InstanceMethodPair) {
        InstanceMethodPair pair = (InstanceMethodPair) o;
        if (pair.getFixtureInstance() == commandObject && methodName.equals(pair.getMethod().getName())) {
          return true;
        }
      }
      return false;
    }
  }

  private class TestClassWithOrdinaryCommand implements HasCommands {

    @FitCommand("")
    public void annotated() {

    }

    public void notAnnotated() {

    }
  }
}
