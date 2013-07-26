package htmlFileFolderNavigator;

import java.io.File;
import java.io.FileFilter;

public class HTMLFileFilter implements FileFilter{

	@Override
	public boolean accept(File pathname) {
		return pathname != null && (pathname.isDirectory() || pathname.getAbsolutePath().endsWith(".html"));
	}

}
