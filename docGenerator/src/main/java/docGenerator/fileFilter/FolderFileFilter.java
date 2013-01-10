package docGenerator.fileFilter;

import java.io.File;
import java.io.FileFilter;

public class FolderFileFilter implements FileFilter{

	@Override
	public boolean accept(File pathname) {
		return pathname != null && pathname.isDirectory();
	}

}
