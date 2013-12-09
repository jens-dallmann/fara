import directoryCrawler.ActionType;
import directoryCrawler.CrawlerAction;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;

public class CopyResultFileCrawlerAction implements CrawlerAction {

  private File folderToCopyTo;

  public CopyResultFileCrawlerAction(File folderToCopyTo ) {
    try {
      FileUtils.forceMkdir(folderToCopyTo);
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.folderToCopyTo = folderToCopyTo;
  }

  @Override
  public void execute(File input) {
    File file = new File(folderToCopyTo.getAbsolutePath()+File.separator+input.getName());
    try {
      FileUtils.copyFile(input,file,"UTF-8", null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public ActionType getType() {
    return ActionType.FILE;
  }

  @Override
  public String getName() {
    return "Copy Result Folder";
  }

  @Override
  public boolean isExecutable(File file) {
    return file != null && !file.isDirectory() && (file.getAbsolutePath().endsWith("_result.html") || file.getAbsolutePath().contains("fitnesse.css")) ;
  }
}
