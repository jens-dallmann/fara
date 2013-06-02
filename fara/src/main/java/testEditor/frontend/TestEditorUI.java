package testEditor.frontend;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import testEditor.frontend.menubar.MenubarController;

public class TestEditorUI {
	private JFrame frame;
	private JComponent tablePanel;
	private JPanel panel;
	private JComponent tree;
	
	public TestEditorUI(String title) {
		frame = new JFrame(title);
		panel = new JPanel();
		panel.setLayout(new MigLayout("", "fill,grow"));
		frame.add(panel);
		frame.setSize(1024,768);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	public void setMenuBar(JMenuBar menubar) {
		frame.setJMenuBar(menubar);
	}
	public void addPanel(JComponent panel) {
		this.panel.add(panel,"wrap");
		frame.validate();
	}
	
	public void close() {
		frame.setVisible(false);
	}

	public void addTablePanel(JComponent panel) {
		if(tablePanel != null) {
			this.panel.remove(tablePanel);
		}
		this.panel.add(panel,2);
		tablePanel = panel;
		frame.validate();
	}

	public JFrame getFrame() {
		return frame;
	}

	public void addTreePanel(JComponent newTree) {
		if(newTree != null) {
			this.panel.remove(newTree);
		}
		this.panel.add(newTree,1);
		tree = newTree;
		frame.validate();
	}
}
