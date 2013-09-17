package commandUtils;

import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.container.CommandResult;

/**
 * Class to parse inputs.
 */
public class ParseUtils {
  /**
   * Parses the input to an integer.
   * If it fails the parameter number, a message and the state wrong will
   * be set to the result.
   *
   * @param input           the input to parse
   * @param result          the result which will be modified if the parse fails
   * @param parameterNumber the parameter number for the result
   * @return the result of the parse
   */
  public int readIntegerInput(String input, CommandResult result, int parameterNumber) {
    int rowCountInput = 0;
    try {
      rowCountInput = Integer.parseInt(input);
    } catch (NumberFormatException e) {
      result.setFailureMessage("Not a correct number");
      result.setWrongParameterNumber(parameterNumber);
      result.setResultState(CommandResultState.WRONG);
    }
    return rowCountInput;
  }
}
