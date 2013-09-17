package fest.swing;

import fest.FestResultBuilder;
import fest.interfaces.LabelUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;
import org.fest.swing.exception.ComponentLookupException;
import org.fest.swing.fixture.JLabelFixture;

public class SwingLabelUIAdapter implements LabelUIAdapter, HasCommands {

  private final SwingFrameWrapper frameWrapper;

  public SwingLabelUIAdapter(SwingFrameWrapper frameWrapper) {
    this.frameWrapper = frameWrapper;

  }

  @FitCommand({"The name of the label", "the text on the label"})
  @Override
  public CommandResult checkLabelText(String labelname, String text) {
    CommandResult result = new CommandResult();
    JLabelFixture fixture = allocateLabel(labelname, result);
    if (result.getResultState() != CommandResultState.WRONG) {
      if (fixture.text().equals(text)) {
        result.setResultState(CommandResultState.RIGHT);
      } else {
        FestResultBuilder.buildWrongResultWrongText(result);
      }
    } else {
      FestResultBuilder.buildWrongResultComponentFailure(result, labelname);
    }
    return result;
  }

  private JLabelFixture allocateLabel(String labelname, CommandResult result) {
    try {
      return frameWrapper.getFrameFixture().label(labelname);
    } catch (ComponentLookupException e) {
      FestResultBuilder.buildWrongResultComponentFailure(result, labelname);
      return null;
    }
  }

}
