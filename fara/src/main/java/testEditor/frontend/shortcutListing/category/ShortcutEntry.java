package testEditor.frontend.shortcutListing.category;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ShortcutEntry {
	private JPanel panel;
	private String BACKGROUND_ODD = "888888";
	private String BACKGROUND_EVEN = "444444";
	private String backgroundColor;
	 
	public ShortcutEntry(String shortcut, String description, boolean odd) {
		if(odd) {
			backgroundColor = BACKGROUND_ODD;
		}
		else {
			backgroundColor =BACKGROUND_EVEN;
		}
		panel = new JPanel();
		panel.setLayout(new MigLayout("","[90]20[250]"));
		panel.add(new JLabel(htmlFormat(shortcut)));
		String descriptionFormatted = htmlFormat(description);
		panel.add(new JLabel(descriptionFormatted));
	}
	
	private String htmlFormat(String description) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<html><body color=\""+backgroundColor+"\">");
		buffer.append(description);
		buffer.append("</body></html>");
		return buffer.toString();
	}

	public JComponent getEntry() {
		return panel;
	}
}
