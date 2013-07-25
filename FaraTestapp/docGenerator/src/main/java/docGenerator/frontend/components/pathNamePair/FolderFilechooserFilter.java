package docGenerator.frontend.components.pathNamePair;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FolderFilechooserFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		return f != null && f.exists() && f.isDirectory();
	}

	@Override
	public String getDescription() {
		return "Folder";
	}
}
