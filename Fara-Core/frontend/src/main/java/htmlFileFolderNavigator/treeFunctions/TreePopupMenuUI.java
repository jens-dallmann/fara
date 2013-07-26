package htmlFileFolderNavigator.treeFunctions;

import java.awt.Point;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class TreePopupMenuUI {
	private JPopupMenu menu;
	private JMenuItem createFolderItem;
	
	public TreePopupMenuUI(Point locationOnScreen)  {
		menu = new JPopupMenu("Options");
		createFolderItem = new JMenuItem("Create Folder");
		
		menu.add(createFolderItem);
		menu.setLocation(locationOnScreen);
		menu.setVisible(true);
	}

	public JPopupMenu getMenu() {
		return menu;
	}
	
	public JMenuItem getCreateFolderItem() {
		return createFolderItem;
	}
	
	public void closePopupmenu() {
		menu.setVisible(false);
	}

	public void setNewLocation(Point locationOnScreen) {
		menu.setLocation(locationOnScreen);
	}

	public void setVisible(boolean b) {
		menu.setVisible(true);
	}
}
