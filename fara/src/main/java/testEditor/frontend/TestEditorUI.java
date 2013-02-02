package testEditor.frontend;

import javax.swing.JComponent;
import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

public class TestEditorUI {
	private JFrame frame;
	
	public TestEditorUI() {
		frame = new JFrame("Fara Test Editor");
		frame.setSize(800,800);
		frame.setLayout(new MigLayout("flowy","fill,grow"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void addPanel(JComponent panel) {
		frame.add(panel);
	}
	public void close() {
		frame.setVisible(false);
	}
}
