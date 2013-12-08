package directoryCrawler;

import java.io.File;

/**
 * An Action for the DirectoryCrawler.
 */
public interface CrawlerAction {

  /**
   * Executes the given file.
   *
   * @param input the file to execute
   */
  void execute(File input);

  /**
   * The action type (Directory or File). For logging only.
   *
   * @return the action type for this action
   */
  ActionType getType();

  /**
   * The name of the action. For logging only.
   *
   * @return the name of the action
   */
  String getName();

  /**
   *
   * @param file the will which is asked to execute
   * @return true if this file can be executed by this action, else false
   */
  boolean isExecutable(File file);
}
