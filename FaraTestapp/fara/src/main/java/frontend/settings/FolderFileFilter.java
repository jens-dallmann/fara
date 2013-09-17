package frontend.settings;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class FolderFileFilter extends FileFilter {
  @Override
  public boolean accept(File pathname) {
    return pathname.isDirectory();
  }

  @Override
  public String getDescription() {
    return "Folders only";
  }
}
