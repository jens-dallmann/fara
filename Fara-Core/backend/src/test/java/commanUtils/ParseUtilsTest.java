package commanUtils;

import commandUtils.ParseUtils;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.container.CommandResult;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the class ParseUtils
 */
public class ParseUtilsTest {

  @Test
  public void testReadIntegerInput() {
    //parsing an integer should pass
    CommandResult result = new CommandResult();
    ParseUtils parseUtils = new ParseUtils();
    int numberThree = parseUtils.readIntegerInput("3", result, 1);
    assertEquals(3, numberThree);
    assertEquals(CommandResultState.IGNORE, result.getResultState());

    //Try to parse a double to an int should fail
    result = new CommandResult();
    int numberDouble = parseUtils.readIntegerInput("2.4", result, 3);
    assertEquals(0, numberDouble);
    wrongAssertionsForReadIntegerInput(result);

    //Try to parse any string containing characters which are not a number should fail
    result = new CommandResult();
    int numberAnyString = parseUtils.readIntegerInput("asdae343.3434ä#", result, 3);
    assertEquals(0, numberAnyString);
    wrongAssertionsForReadIntegerInput(result);
  }

  private void wrongAssertionsForReadIntegerInput(CommandResult result) {
    assertEquals(CommandResultState.WRONG, result.getResultState());
    assertEquals("Not a correct number", result.getFailureMessage());
    assertEquals(3, result.getWrongParameterNumber());
  }

}
