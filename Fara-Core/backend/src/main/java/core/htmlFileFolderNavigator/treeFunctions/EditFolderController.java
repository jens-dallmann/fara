package core.htmlFileFolderNavigator.treeFunctions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class EditFolderController {
	private EditFolderUI ui;
	
	public EditFolderController(JFrame frame, String oldName) {
		ui = new EditFolderUI(frame, oldName);
		addListeners();
		ui.setVisible();
	}

	private void addListeners() {
		ui.getApproveButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ui.setInvisible();
			}
		});
		ui.getCancelButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
	public String getNewName() {
		return ui.getTextFieldText();
	}
}
