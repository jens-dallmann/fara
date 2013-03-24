package core.exception.frontend;

import java.awt.Window;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

public class ExceptionWindowUI {
	
	private JPanel captionPanel;
	private JLabel errorLevel;
	private JLabel caption;
	
	private JTextArea area;
	private JDialog dialog;
	private JPanel uiPanel;
	
	public ExceptionWindowUI(Window parent, ImageIcon errorLevelImage, String captionText) {
		createPanel(errorLevelImage, captionText);
		dialog = new JDialog(parent);
		dialog.add(uiPanel);
		dialog.setSize(600,500);
	}

	private void createPanel(ImageIcon errorLevelImage, String captionText) {
		createCaptionPanel(errorLevelImage, captionText);
		
		uiPanel = new JPanel();
		uiPanel.setLayout(new MigLayout("","[grow,fill]","[][grow,fill]"));
		area = new JTextArea();
		area.setRows(20);
		area.setEditable(false);
		JScrollPane scrollpane = new JScrollPane(area);
		uiPanel.add(captionPanel, "wrap");
		uiPanel.add(scrollpane);
	}

	private void createCaptionPanel(ImageIcon errorLevelImage,
			String captionText) {
		captionPanel = new JPanel();
		captionPanel.setLayout(new MigLayout("","[]40[grow,fill]"));
		errorLevel = new JLabel();
		errorLevel.setIcon(errorLevelImage);
		caption = new JLabel(captionText);
		
		
		captionPanel.add(errorLevel);
		captionPanel.add(caption);
	}
	
	public JDialog getDialog() {
		return dialog;
	}
	
	public JTextArea getArea() {
		return area;
	}
}
