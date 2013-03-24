package testEditor.frontend.persistenceToolbar;

import java.io.File;

public interface PersistenceToolbarDelegate {

	public void save();

	public void load(File file);
	
	public boolean hasFile();
	
	public void saveAs(File file);
}
