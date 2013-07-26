package htmlFileFolderNavigator.utils;

import java.io.File;

public class WrappedFile {
	private File file;
	
	public WrappedFile(File newFile) {
		file = newFile;
	}
	
	@Override
	public String toString() {
		return file.getName();
	}

	public File getFile() {
		return file;
	}
}
