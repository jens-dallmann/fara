package fest.swing;

import java.awt.Component;

import javax.swing.JLabel;

import org.fest.swing.fixture.JLabelFixture;

import fest.FestResultBuilder;
import fest.interfaces.LabelUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class SwingLabelUIAdapter implements LabelUIAdapter, HasCommands{

	private final SwingFrameWrapper frameWrapper;
	
	public SwingLabelUIAdapter(SwingFrameWrapper frameWrapper) {
		this.frameWrapper = frameWrapper;
		
	}
	@FitCommand({"The name of the label", "the text on the label"})
	@Override
	public CommandResult checkLabelText(String labelname, String text) {
		CommandResult result = new CommandResult();
		Component component = frameWrapper.findComponentByName(labelname);
		if(component instanceof JLabel) {
			JLabel label = (JLabel) component;
			JLabelFixture fixture = new JLabelFixture(frameWrapper.getRobot(), label);
			if(fixture.text().equals(text)) {
				result.setResultState(CommandResultState.RIGHT);
			}
			else {
				FestResultBuilder.buildWrongResultWrongText(result);
			}
		} else {
			FestResultBuilder.buildWrongResultComponentFailure(result, labelname);
		}
		return result;
	}
	
}
