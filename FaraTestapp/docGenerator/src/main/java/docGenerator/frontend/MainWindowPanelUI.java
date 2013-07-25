package docGenerator.frontend;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class MainWindowPanelUI {

	private JPanel panel;
	private JButton generateButton;
	
	public MainWindowPanelUI() {
		panel = new JPanel();
		panel.setLayout(new MigLayout("flowy","fill,grow"));
		generateButton = new JButton("Generate");
		panel.add(generateButton);
	}
	
	public void addJPanel(JPanel newPanel) {
		int componentCount = panel.getComponentCount();
		panel.add(newPanel, componentCount-1);
		panel.revalidate();
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public JButton getGenerateButton() {
		return generateButton;
	}
}
