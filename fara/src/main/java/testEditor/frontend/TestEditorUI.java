package testEditor.frontend;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class TestEditorUI {
	private JFrame frame;
	private JComponent tablePanel;
	private JPanel panel;
	
	public TestEditorUI() {
		frame = new JFrame("Fara Test Editor");
		panel = new JPanel();
		panel.setLayout(new MigLayout("flowy", "fill,grow"));
		frame.add(panel);
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void addPanel(JComponent panel) {
		this.panel.add(panel);
		frame.validate();
	}
	public void close() {
		frame.setVisible(false);
	}

	public void addTablePanel(JComponent panel) {
		if(tablePanel != null) {
			this.panel.remove(tablePanel);
		}
		this.panel.add(panel,1);
		tablePanel = panel;
		frame.validate();
	}
}
