package directoryCrawler;

import org.slf4j.LoggerFactory;

public class NoActionsToExecuteException extends Exception {

  public NoActionsToExecuteException(Class classToLogTo) {
    super("Directory Crawler: No Actions to Execute");
    LoggerFactory.getLogger(classToLogTo).info("No Actions that can be executed. So nothing to do");
  }
}
