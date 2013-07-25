package filechooserTab;

import interfaces.TabUI;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class FilechooserTabUI implements TabUI{

	private JPanel tabPanel;
	private JButton openFileChooserButton;
	
	public FilechooserTabUI() {
		tabPanel = new JPanel(new MigLayout());
		openFileChooserButton = new JButton("Open Filechooser");
		openFileChooserButton.setName("openFilechooserButton");
		tabPanel.add(openFileChooserButton);
	}
	@Override
	public JPanel getTabPanel() {
		return tabPanel;
	}
	public JButton getButton() {
		return openFileChooserButton;
	}
}
