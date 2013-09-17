package fest.swing;

import fest.FestResultBuilder;
import fest.interfaces.RadioButtonUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.fixture.JRadioButtonFixture;

public class SwingRadioButtonUIAdapter implements HasCommands, RadioButtonUIAdapter {

  private SwingFrameWrapper frameWrapper;

  public SwingRadioButtonUIAdapter(SwingFrameWrapper frameWrapper) {
    this.frameWrapper = frameWrapper;
  }

  @FitCommand({"The name of the radio button which should be selected"})
  @Override
  public CommandResult selectRadioButton(String radioButtonName) {
    CommandResult result = new CommandResult();
    JRadioButtonFixture radioButton = allocateRadioButton(radioButtonName, result);
    if (result.getResultState() != CommandResultState.WRONG) {
      try {
        radioButton.check();
      } catch (IllegalArgumentException e) {
        result.setFailureMessage("RadioButton is not visible or is disabled");
        result.setResultState(CommandResultState.WRONG);
        result.setWrongParameterNumber(1);
      }
    }
    return result;
  }

  private JRadioButtonFixture allocateRadioButton(String radioButtonName, CommandResult result) {
    try {
      return frameWrapper.getFrameFixture().radioButton(radioButtonName);
    } catch (ComponentLookupException e) {
      FestResultBuilder.buildWrongResultComponentFailure(result, radioButtonName);
      return null;
    }
  }
}
