package fest.swing;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.fest.swing.fixture.JFileChooserFixture;
import org.fest.swing.timing.Timeout;

import fest.FestResultBuilder;
import fest.interfaces.FileChooserUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;

public class SwingFileChooserAdapter implements HasCommands,
		FileChooserUIAdapter {

	private final SwingFrameWrapper frameWrapper;

	public SwingFileChooserAdapter(SwingFrameWrapper frameWrapper) {
		this.frameWrapper = frameWrapper;
	}

	@FitCommand({})
	public CommandResult useFileChooserWithResource(String resource) {
		CommandResult result = new CommandResult();
		JFileChooserFixture fileChooser = frameWrapper.getFrameFixture().fileChooser(Timeout.timeout(3000));
		if (fileChooser != null) {
			String resourcePath = null;
			try {
				resourcePath = loadResourcePath(resource);
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

	private String loadResourcePath(String resource) throws URISyntaxException {
		ClassLoader classLoader = SwingFileChooserAdapter.class
				.getClassLoader();
		URL resourceUrl = classLoader.getResource(resource);
		File resourceFile = new File(resourceUrl.toURI());
		return resourceFile.getAbsolutePath();
	}

}
