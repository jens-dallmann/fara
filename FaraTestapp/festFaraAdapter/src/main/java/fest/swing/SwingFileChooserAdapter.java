package fest.swing;

import java.io.File;
import java.net.URISyntaxException;

import org.fest.swing.fixture.JFileChooserFixture;
import org.fest.swing.timing.Timeout;

import core.service.RessourceService;
import fest.FestResultBuilder;
import fest.interfaces.FileChooserUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class SwingFileChooserAdapter implements HasCommands,
		FileChooserUIAdapter {

	private final SwingFrameWrapper frameWrapper;
	private final RessourceService ressourceService;

	public SwingFileChooserAdapter(SwingFrameWrapper frameWrapper) {
		this.frameWrapper = frameWrapper;
		ressourceService = new RessourceService();
	}

	@FitCommand({ "The name of the resource file which should be selected" })
	public CommandResult useFileChooserWithResource(String resource) {
		CommandResult result = new CommandResult();
		JFileChooserFixture fileChooser = frameWrapper.getFrameFixture()
				.fileChooser(Timeout.timeout(3000));
		if (fileChooser != null) {
			String resourcePath = null;
			try {
				resourcePath = ressourceService.loadRessourceFilePath(resource);
			} catch (URISyntaxException e) {
				result.setFailureMessage("No valid Resource");
				result.setResultState(CommandResultState.WRONG);
				result.setWrongParameterNumber(2);
				return result;
			}
			fileChooser.fileNameTextBox().setText(resourcePath);
			fileChooser.approve();
			result.setResultState(CommandResultState.RIGHT);
		} else {
			result = FestResultBuilder.buildWrongResultComponentFailure(result,
					"File chooser");
		}
		return result;
	}

	@FitCommand({ "The name of the file in the home directory" })
	public CommandResult useFileChooserFromHomeDirectory(String filepath) {
		CommandResult result = new CommandResult();
		JFileChooserFixture fileChooser = frameWrapper.getFrameFixture()
				.fileChooser(Timeout.timeout(3000));
		if (fileChooser != null) {
			String ressourcePath = System.getProperty("user.home")
					+ File.separator + filepath;
			fileChooser.fileNameTextBox().setText(ressourcePath);
			fileChooser.approve();
			result.setResultState(CommandResultState.RIGHT);
		} else {
			result = FestResultBuilder.buildWrongResultComponentFailure(result,
					"File chooser");
		}
		return result;
	}
}
