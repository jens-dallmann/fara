package testEditor.frontend.settings;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class TestSuiteSettingsUI {
	
	private JDialog dialog;
	private JTextField testSuiteFolder;
	private JButton chooseFolderButton;
	private JButton approveButton;
	private JButton cancelButton;
	
	public TestSuiteSettingsUI(JFrame owner) {
		dialog = new JDialog(owner, true);
		dialog.setTitle("TestSuite Settings");
		dialog.setLayout(new MigLayout("nogrid"));
		dialog.setSize(500,300);
		approveButton = new JButton("Approve");
		cancelButton = new JButton("Cancel");
		dialog.add(buildTestSuiteFolderChooser(),"wrap");
		dialog.add(approveButton);
		dialog.add(cancelButton);
	}
	private JPanel buildTestSuiteFolderChooser() {
		JPanel folderChooser = new JPanel();
		testSuiteFolder = new JTextField();
		testSuiteFolder.setEditable(false);
		chooseFolderButton = new JButton("<html><body>Choose Folder</body></html>");
		
		folderChooser.setLayout(new MigLayout("","[90][250,fill][140]"));
		folderChooser.add(new JLabel("<html><body>TestSuite root folder</body></html>"));
		folderChooser.add(testSuiteFolder);
		folderChooser.add(chooseFolderButton);
		return folderChooser;
	}
	
	public JButton getChooseFolderButton() {
		return chooseFolderButton;
	}
	
	public void setTestSuiteFolderText(String text) {
		testSuiteFolder.setText(text);
	}
	
	public JButton getApproveButton() {
		return approveButton;
	}
	
	public JButton getCancelButton() {
		return cancelButton;
	}
	
	public void setDialogInvisible() {
		dialog.setVisible(false);
	}
	public String getRootFolderPath() {
		return testSuiteFolder.getText();
	}
	public void setVisible() {
		dialog.setVisible(true);
	}
}
