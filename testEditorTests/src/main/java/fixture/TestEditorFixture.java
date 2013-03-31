package fixture;

import javax.swing.JFrame;

import org.fest.swing.edt.GuiQuery;

import testEditor.frontend.TestEditorController;
import fest.swing.SwingFrameWrapper;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class TestEditorFixture implements HasCommands {
	private SwingFrameWrapper wrapper;

	public TestEditorFixture(SwingFrameWrapper wrapper) {
		this.wrapper = wrapper;
	}
	@FitCommand({})
	public CommandResult startEditor() {
		final CommandResult result = new CommandResult();
		result.setResultState(CommandResultState.WRONG);
		wrapper.init(new GuiQuery<JFrame>() {
			@Override
			protected JFrame executeInEDT() throws Throwable {
				result.setResultState(CommandResultState.RIGHT);
				return new TestEditorController(new DummyFixture(), null, "Another Frame").getFrame();
			}
		});
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
}
