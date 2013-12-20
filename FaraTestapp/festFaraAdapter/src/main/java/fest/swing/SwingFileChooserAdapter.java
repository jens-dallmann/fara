package fest.swing;

import core.service.RessourceService;
import fest.FestResultBuilder;
import fest.interfaces.FileChooserUIAdapter;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;
import org.fest.swing.fixture.JFileChooserFixture;
import org.fest.swing.timing.Timeout;

import java.io.File;
import java.net.URISyntaxException;

public class SwingFileChooserAdapter implements HasCommands,
        FileChooserUIAdapter {

  private final SwingFrameWrapper frameWrapper;
  private final RessourceService ressourceService;

  public SwingFileChooserAdapter(SwingFrameWrapper frameWrapper) {
    this.frameWrapper = frameWrapper;
    ressourceService = new RessourceService();
  }

  @FitCommand({"The name of the resource file which should be selected"})
  public CommandResult useFileChooserWithResource(String resource) {
    CommandResult result = new CommandResult();
    String resourcePath = null;
    try {
      resourcePath = ressourceService.loadRessourceFilePath(this.getClass(), resource);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    result = setFileChooserContent(result, resourcePath);
    return result;
  }

  @FitCommand({"The path to the file"})
  public CommandResult useFileChooser(String absolutePath) {
    return setFileChooserContent(new CommandResult(), absolutePath);
  }

  private CommandResult setFileChooserContent(CommandResult result, String resourcePath) {
    JFileChooserFixture fileChooser = frameWrapper.getFrameFixture()
            .fileChooser(Timeout.timeout(3000));
    if (fileChooser != null) {
      String testdataDirectory = null;
      fileChooser.fileNameTextBox().setText(resourcePath);

      fileChooser.approve();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
      }
      result.setResultState(CommandResultState.RIGHT);
    } else {
      result = FestResultBuilder.buildWrongResultComponentFailure(result,
              "File chooser");
    }
    return result;
  }

  @FitCommand({"The name of the file in the home directory"})
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
