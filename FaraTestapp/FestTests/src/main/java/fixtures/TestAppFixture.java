package fixtures;

import fest.swing.SwingFrameWrapper;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;
import main.MainFrameController;
import org.fest.swing.edt.GuiQuery;

import javax.swing.JFrame;

public class TestAppFixture implements HasCommands {

  private SwingFrameWrapper frameWrapper;
  private final static long SLEEP_TIME_UNTIL_PROCEED = 3000;

  public TestAppFixture(SwingFrameWrapper frameWrapper) {
    this.frameWrapper = frameWrapper;
  }

  @FitCommand({})
  public CommandResult startTestApp() throws InterruptedException {
    final CommandResult result = new CommandResult();
    result.setResultState(CommandResultState.WRONG);
    frameWrapper.init(new GuiQuery<JFrame>() {
      @Override
      protected JFrame executeInEDT() throws Throwable {
        result.setResultState(CommandResultState.RIGHT);
        return new MainFrameController().getFrame();
      }
    });
    Thread.sleep(SLEEP_TIME_UNTIL_PROCEED);
    return result;
  }
}
