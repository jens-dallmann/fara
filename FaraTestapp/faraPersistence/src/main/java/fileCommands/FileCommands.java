package fileCommands;

import core.service.RessourceService;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class FileCommands implements HasCommands {

  private final RessourceService ressourceSerivce;

  public FileCommands() {
    ressourceSerivce = new RessourceService();
  }

  @FitCommand({"The ressource file path", "the path of the file in the home directory of the system user"})
  public CommandResult copyRessourceToHome(String filename, String expectedNameInHomeDirectory) {
    CommandResult result = new CommandResult();
    String userHome = System.getProperty("user.home");
    File userHomeFile = new File(userHome + File.separator + expectedNameInHomeDirectory);
    try {
      File ressourceFile = ressourceSerivce.loadRessourceFile(filename);
      FileUtils.copyFile(ressourceFile, userHomeFile);
      result.setResultState(CommandResultState.RIGHT);

    } catch (URISyntaxException e) {
      result.setFailureMessage(e.getMessage());
      result.setResultState(CommandResultState.WRONG);
      result.setWrongParameterNumber(1);
    } catch (IOException e) {
      result.setFailureMessage(e.getMessage());
      result.setResultState(CommandResultState.WRONG);
      result.setWrongParameterNumber(1);
    }
    return result;
  }
}
