package testEditor.frontend.shortcutListing;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class ShortcutListingUI {
	private JDialog dialog;
	
	public ShortcutListingUI(List<JPanel> shortcutCategories) {
		dialog = new JDialog();
		dialog.setTitle("Shortcut Listing");
		dialog.setLayout(new GridLayout(shortcutCategories.size(),1));
		for(JPanel oneCategory: shortcutCategories) {
			dialog.add(oneCategory);
		}
		dialog.setSize(400,400);
		dialog.setVisible(true);
	}
}
