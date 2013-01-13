package fixtures;
import javax.swing.JFrame;

import main.MainFrameController;

import org.fest.swing.edt.GuiQuery;

import fest.swing.SwingFrameWrapper;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class TestAppFixture implements HasCommands {

	private SwingFrameWrapper frameWrapper;

	public TestAppFixture(SwingFrameWrapper frameWrapper) {
		this.frameWrapper = frameWrapper;
	}
	
	@FitCommand({})
	public CommandResult startTestApp() {
		final CommandResult result = new CommandResult();
		result.setResultState(CommandResultState.WRONG);
		frameWrapper.init(new GuiQuery<JFrame>() {
			@Override
			protected JFrame executeInEDT() throws Throwable {
				result.setResultState(CommandResultState.RIGHT);
				return new MainFrameController().getFrame();
			}
		});
		return result;
	}
}
