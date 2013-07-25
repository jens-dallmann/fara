package testEditor.frontend.persistenceToolbar;

import java.io.File;

public class HtmlFileFilter extends javax.swing.filechooser.FileFilter{
	@Override
	public boolean accept(File pathname) {
		return pathname != null && pathname.getAbsolutePath().endsWith(".html") || pathname.isDirectory();
	}

	@Override
	public String getDescription() {
		return "*.html";
	}
}
