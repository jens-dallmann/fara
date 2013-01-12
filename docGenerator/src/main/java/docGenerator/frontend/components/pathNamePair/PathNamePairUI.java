package docGenerator.frontend.components.pathNamePair;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class PathNamePairUI {
	
	private JTextField nameTextField;
	private JTextField pathTextField;
	private JLabel nameLabel;
	private JButton filechooser;
	private JPanel panel;
	
	public PathNamePairUI() {
		nameLabel = new JLabel("Name: ");
		nameTextField = new JTextField();
		nameTextField.setName("Name");
		pathTextField = new JTextField();
		pathTextField.setName("Path");
		pathTextField.setEditable(false);
		filechooser = new JButton("Select Class Folder");
		
		panel = new JPanel();
		panel.setLayout(new MigLayout("flowx,align left, insets 0", "[][][grow,fill][]"));
		panel.add(nameLabel);
		panel.add(nameTextField, "width max(175, 25%)");
		panel.add(pathTextField);
		panel.add(filechooser);
	}
	
	public String getPath() {
		return pathTextField.getText();
	}
	
	public String getName() {
		return nameTextField.getText();
	}
	
	public JButton getFilechooserButton() {
		return filechooser;
	}
	
	public void setPath(String newPath) {
		pathTextField.setText(newPath);
	}

	public JPanel getPanel() {
		return panel;
	}
}
