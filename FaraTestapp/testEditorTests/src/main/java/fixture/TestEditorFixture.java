package fixture;

import fest.swing.SwingFrameWrapper;
import fitArchitectureAdapter.CommandResultState;
import fitArchitectureAdapter.annotations.FitCommand;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.interfaces.HasCommands;
import frontend.TestEditorController;
import org.fest.swing.edt.GuiQuery;

import javax.swing.JFrame;
import java.io.File;

public class TestEditorFixture implements HasCommands {
  private SwingFrameWrapper wrapper;
  private TestEditorController testEditor;

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
        testEditor = new TestEditorController(new DummyFixture(), null, "Another Frame");
        JFrame frame = testEditor.getFrame();
        return frame;
      }
    });
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return result;
  }

  @FitCommand({})
  public CommandResult checkResultFileExist() {
    CommandResult result = new CommandResult();
    File resultFolderFile = createResultFolderFile();
    File[] listFiles = resultFolderFile.listFiles();

    if (listFiles != null && listFiles.length > 1) {
      boolean containsOne = false;
      for (File oneFile : listFiles) {
        if (oneFile.getAbsolutePath().endsWith("_result.html")) {
          result.setResultState(CommandResultState.RIGHT);
          containsOne = true;
          break;
        }
      }
      if (!containsOne) {
        result.setFailureMessage("No Result File exist");
        result.setResultState(CommandResultState.WRONG);
        result.setWrongParameterNumber(0);
      }
    } else {
      result.setFailureMessage("No Result File exist");
      result.setResultState(CommandResultState.WRONG);
      result.setWrongParameterNumber(0);
    }
    return result;
  }

  @FitCommand({})
  public CommandResult checkNoResultFileExist() {
    CommandResult result = new CommandResult();
    CommandResult invertedResult = checkResultFileExist();
    if (invertedResult.getResultState() == CommandResultState.RIGHT) {
      result.setFailureMessage("Result File exist!!");
      result.setResultState(CommandResultState.WRONG);
      result.setWrongParameterNumber(0);
    } else {
      result.setResultState(CommandResultState.RIGHT);
    }
    return result;
  }

  private File createResultFolderFile() {
    File loadedTestFile = testEditor.getLoadedFile();
    String absolutePath = loadedTestFile.getAbsolutePath();
    int lastIndexOf = absolutePath.lastIndexOf(File.separator);
    String pathToLastFolder = absolutePath.substring(0, lastIndexOf);
    String resultFolder = pathToLastFolder + File.separator + "result" + File.separator;
    File resultFolderFile = new File(resultFolder);
    return resultFolderFile;
  }

  @FitCommand({})
  public CommandResult deleteResultFolder() {
    CommandResult result = new CommandResult();
    File resultFolder = createResultFolderFile();
    deleteFiles(resultFolder);
    boolean deleted = resultFolder.delete();
    if (deleted) {
      result.setResultState(CommandResultState.RIGHT);
    } else {
      result.setFailureMessage("Can not delete result folder: " + resultFolder.getAbsolutePath());
      result.setResultState(CommandResultState.WRONG);
      result.setWrongParameterNumber(0);
    }
    return result;
  }

  @FitCommand({})
  public CommandResult endEditor() {
    CommandResult result = new CommandResult();
    wrapper.cleanUpRobot();
    result.setResultState(CommandResultState.RIGHT);
    return result;
  }

  private void deleteFiles(File resultFolder) {
    File[] listFiles = resultFolder.listFiles();
    if (listFiles != null) {
      for (File file : listFiles) {
        file.delete();
      }
    }
  }
}
