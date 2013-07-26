package docGenerator.frontend;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindowUI {

	private JFrame frame;
	public MainWindowUI() {
		frame = new JFrame("Documentation Generator");
		frame.setSize(700,300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				Dimension size = e.getComponent().getSize();
				size.height = 300;
				e.getComponent().setSize(size);
			}
		});
	}
	public void addPanel(JPanel panel) {
		frame.add(panel);
		frame.setVisible(true);
	}
}
