package testEditor.frontend.shortcutListing.category;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ShortcutCategoryUI {
	private JLabel title;
	
	private JPanel panel;
	
	public ShortcutCategoryUI(String title, JPanel shortcuts) {
		this.title = new JLabel(title);
		panel = new JPanel();
		panel.setLayout(new MigLayout());
		panel.add(this.title, "wrap");
		panel.add(shortcuts);
	}
	
	public JPanel getPanel() {
		return panel;
	}
}
