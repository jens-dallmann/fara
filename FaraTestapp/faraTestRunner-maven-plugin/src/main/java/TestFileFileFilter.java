import java.io.File;
import java.io.FileFilter;

/**
 * Created with IntelliJ IDEA.
 * User: Deception
 * Date: 26.07.13
 * Time: 21:53
 * To change this template use File | Settings | File Templates.
 */
public class TestFileFileFilter implements FileFilter {
  @Override
  public boolean accept(File pathname) {
    boolean isTestFile = pathname.getAbsolutePath().endsWith(".html");
    boolean isDirectory = pathname.isDirectory();
    return isDirectory || isTestFile;
  }
}
