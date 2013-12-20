package fitArchitectureAdapter;

import core.ProcessListener;
import fit.Parse;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class AbstractActionFixtureAggregatorDoCellsTest {

  private AbstractActionFixtureAggregator aggregator;

  @Mock
  private ProcessListener listener;

  @Before
  public void beforeEachTest() {
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
  public void testDoCellsProcessRight() {
    Parse filledRow = createFilledRow("processRight");

    aggregator.addCommandObject(new ProcessRightTestClass());
    aggregator.doCells(filledRow.parts);

    verify(listener, times(1)).publishResult(
            CommandResultState.RIGHT.toString(), null);
  }

  @Test
  public void testDoCellsProcessWrong() {
    Parse filledRow = createFilledRow("processWrong", "firstParameter");

    aggregator.addCommandObject(new ProcessCommandFails());
    AbstractActionFixtureAggregator spy = spy(aggregator);

    spy.doCells(filledRow.parts);
    verify(listener).publishResult(CommandResultState.WRONG.toString(), "processWrong");
    verify(spy, times(1)).wrong(filledRow.parts.more);
  }

  @Test
  public void testDoCellsCommandHasWrongReturnType() {
    Parse wrongType = createFilledRow("wrongType", "parameter 1");

    aggregator.addCommandObject(new ProcessCommandWithWrongType());
    aggregator.doCells(wrongType.parts);
    verify(listener, times(0)).publishResult(anyString(), anyString());
  }

  @Test
  public void testDoCellsCommandNotFound() {
    Parse commandNotFoundRow = createFilledRow("notExistingCommand");

    aggregator.addCommandObject(new ProcessRightTestClass());
    AbstractActionFixtureAggregator spy = spy(aggregator);
    spy.doCells(commandNotFoundRow.parts);

    verify(spy, times(1)).wrong(commandNotFoundRow.parts, "Command not found: notExistingCommand");
    verify(listener, times(1)).publishResult(CommandResultState.WRONG.toString(), "Command not found: notExistingCommand");
  }

  @Test
  public void testDoCellsIgnoreCommand() {
    Parse commandNotAccesable = createFilledRow("ignoreResult");

    aggregator.addCommandObject(new ProcessIgnoredCommand());

    AbstractActionFixtureAggregator spy = spy(aggregator);

    spy.doCells(commandNotAccesable.parts);

    verify(listener, times(1)).publishResult(CommandResultState.IGNORE.toString(), null);
  }

  private Parse createFilledRow(String... commands) {
    HtmlTableUtils utils = new HtmlTableUtils();
    List<String> list = new ArrayList<String>();
    for (String command : commands) {
      list.add(command);
    }
    return utils.createFilledRow(list);
  }

  private class ProcessRightTestClass implements HasCommands {

    @FitCommand("")
    public CommandResult processRight() {
      CommandResult result = new CommandResult();
      result.setResultState(CommandResultState.RIGHT);
      return result;
    }
  }

  private class ProcessCommandFails implements HasCommands {
    @FitCommand("")
    public CommandResult processWrong(String oneParameter) {
      CommandResult result = new CommandResult();
      result.setFailureMessage("processWrong");
      result.setResultState(CommandResultState.WRONG);
      result.setWrongParameterNumber(1);
      return result;
    }
  }

  private class ProcessCommandWithWrongType implements HasCommands {

    @FitCommand("")
    public String wrongType(String oneParameter) {
      return "";
    }
  }

  private class ProcessIgnoredCommand implements HasCommands {
    @FitCommand("")
    public CommandResult ignoreResult() {
      return new CommandResult();
    }
  }
}
