package core.htmlFileFolderNavigator.utils;

import java.io.File;

public class RootWrappedFile extends WrappedFile{

	public RootWrappedFile(File newFile) {
		super(newFile);
	}
	
	@Override
	public String toString() {
		return getFile().toString();
	}
}
