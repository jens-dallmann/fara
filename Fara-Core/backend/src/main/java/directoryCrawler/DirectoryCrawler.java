package directoryCrawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Useful to process files in directories. You can apply actions on single files.
 * By using the file filter and the actions isExecutable() method wisely you can
 * process file sets with different actions.
 */
public class DirectoryCrawler {

  private static final Logger LOG = LoggerFactory.getLogger(DirectoryCrawler.class);

  public void crawlDirectory(File directoryPath, CrawlerAction directoryAction, CrawlerAction fileAction, boolean recursively) throws NoActionsToExecuteException {
    ArrayList<CrawlerAction> fileActions = null;
    if(fileAction != null) {
      fileActions = new ArrayList<CrawlerAction>();
      fileActions.add(fileAction);
    }

    ArrayList<CrawlerAction> directoryActions = null;
    if(directoryAction != null) {
      directoryActions = new ArrayList<CrawlerAction>();
      directoryActions.add(directoryAction);
    }

    crawlDirectory(directoryPath, directoryActions, fileActions, recursively);
  }

  public void crawlDirectory(File directoryPath, FileFilter crawledFilesFilter, CrawlerAction directoryAction, CrawlerAction fileAction, boolean recursively) throws NoActionsToExecuteException {
    ArrayList<CrawlerAction> fileActions = new ArrayList<CrawlerAction>();
    fileActions.add(fileAction);
    ArrayList<CrawlerAction> directoryActions = new ArrayList<CrawlerAction>();
    directoryActions.add(directoryAction);
    crawlDirectory(directoryPath, crawledFilesFilter, directoryActions, fileActions, recursively);
  }
  /**
   * Crawles over a directory and executes the given actions on the files.
   * If you want to crawl the directory with subdirectories you have to set the flag recursively to true
   *
   * @param directoryPath    directory which should be crawled
   * @param directoryActions actions which are executed on directories. List will not be resorted.
   * @param fileActions      actions which are executed on files. List will not be resorted.
   * @param recursively      true if you want to crawl down the hierarchy, else false
   */
  public void crawlDirectory(File directoryPath, List<CrawlerAction> directoryActions, List<CrawlerAction> fileActions, boolean recursively) throws NoActionsToExecuteException {
    crawlDirectory(directoryPath, null, directoryActions, fileActions, recursively);
  }

  /**
   * Crawles over a directory and executes the given actions on the files.
   * If you want to crawl the directory with subdirectories you have to set the flag recursively to true
   * You can use an explicit file filter for more performant crawling.
   *
   * @param directoryPath      directory which should be crawled
   * @param crawledFilesFilter the file filter you want to use to filter the directories. Make sure to allow directories if you want to crawl recursively
   * @param directoryActions   actions which are executed on directories. List will not be resorted.
   * @param fileActions        actions which are executed on files. List will not be resorted.
   * @param recursively        true if you want to crawl down the hierarchy, else false
   */
  public void crawlDirectory(File directoryPath, FileFilter crawledFilesFilter, List<CrawlerAction> directoryActions, List<CrawlerAction> fileActions, boolean recursively) throws NoActionsToExecuteException {
    //make sure all objects are set
    assert directoryPath != null : "No Directory input";
    assert directoryPath.isDirectory() : directoryPath.getAbsolutePath() + " is not a directory";

    if (directoryActions == null && fileActions == null) {
      throw new NoActionsToExecuteException(DirectoryCrawler.class);
    }

    if (directoryActions == null) {
      LOG.info("No directory actions. Will proceed with File Actions.");
      directoryActions = new ArrayList<CrawlerAction>();
    }
    if (fileActions == null) {
      LOG.info("No file actions. Will proceed with directory actions.");
      fileActions = new ArrayList<CrawlerAction>();
    }

    File[] files;
    if (crawledFilesFilter != null) {
      files = directoryPath.listFiles(crawledFilesFilter);
    } else {
      files = directoryPath.listFiles();
    }

    crawlFiles(files, crawledFilesFilter, directoryActions, fileActions, recursively);
  }

  private void crawlFiles(File[] directoryContent, FileFilter fileFilter, List<CrawlerAction> directoryActions, List<CrawlerAction> fileActions, boolean recursively) throws NoActionsToExecuteException {
    if (directoryContent != null) {
      for (File oneFile : directoryContent) {
        if (oneFile.isDirectory()) {
          handleDirectory(oneFile, fileFilter, directoryActions, fileActions, recursively);
        } else {
          handleFile(oneFile, fileActions);
        }

      }
    }
  }

  private void handleFile(File oneFile, List<CrawlerAction> fileActions) {
    boolean fileActionsExist = fileActions.size() > 0;
    if (fileActionsExist) {
      executeCrawlerActions(oneFile, fileActions);
    }
  }

  private void executeCrawlerActions(File oneFile, List<CrawlerAction> actions) {
    for (CrawlerAction action : actions) {
      if (action.isExecutable(oneFile)) {
        action.execute(oneFile);

        //Logging
        StringBuilder builder = new StringBuilder();
        builder.append("Executed ");
        builder.append(action.getType());
        builder.append(" Action with name: ");
        builder.append(action.getName());
        builder.append(" on ");
        builder.append(oneFile.getAbsolutePath());
        LOG.info(builder.toString());
      }
    }
  }

  private void handleDirectory(File oneFile, FileFilter fileFilter, List<CrawlerAction> directoryActions, List<CrawlerAction> fileActions, boolean recursively) throws NoActionsToExecuteException {
    boolean actionsExist = directoryActions.size() > 0;
    if (actionsExist) {
      executeCrawlerActions(oneFile, directoryActions);
    }
    if (recursively) {
      crawlDirectory(oneFile, fileFilter, directoryActions, fileActions, recursively);
    }
  }
}
