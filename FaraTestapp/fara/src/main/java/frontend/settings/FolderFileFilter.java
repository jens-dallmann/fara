package frontend.settings;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FolderFileFilter extends FileFilter{
	@Override
	public boolean accept(File pathname) {
		return pathname.isDirectory();
	}

	@Override
	public String getDescription() {
		return "Folders only";
	}
}
