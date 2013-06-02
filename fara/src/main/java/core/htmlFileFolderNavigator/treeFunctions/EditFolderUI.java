package core.htmlFileFolderNavigator.treeFunctions;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class EditFolderUI {
	private JDialog dialog;
	private JPanel panel;
	private JTextField textField;
	private JButton approveButton;
	private JButton cancelButton;
	
	public EditFolderUI(JFrame parent, String oldText) {
		dialog = new JDialog(parent,true);
		dialog.setTitle("Name of the new Folder");
		panel = new JPanel();
		panel.setLayout(new MigLayout("nogrid","fill, grow"));
		textField = new JTextField(oldText);
		approveButton = new JButton("Approve");
		cancelButton = new JButton("Cancel");
		
		panel.add(textField, "wrap");
		panel.add(approveButton);
		panel.add(cancelButton);
		dialog.add(panel);
		dialog.setSize(200,100);
	}
	
	public String getTextFieldText() {
		return textField.getText();
	}
	
	public JButton getApproveButton() {
		return approveButton;
	}
	
	public JButton getCancelButton() {
		return cancelButton;
	}

	public void setInvisible() {
		dialog.setVisible(false);
	}

	public void setVisible() {
		dialog.setVisible(true);
	}
}
