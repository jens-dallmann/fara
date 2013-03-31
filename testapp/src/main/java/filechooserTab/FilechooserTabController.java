package filechooserTab;

import interfaces.TabController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class FilechooserTabController implements TabController{

	private FilechooserTabUI ui;
	
	public FilechooserTabController() {
		ui = new FilechooserTabUI();
		ui.getButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser filechooser = new JFileChooser();
				filechooser.setName("filechooser");
				int chooseState = filechooser.showOpenDialog(getTabPanel());
				if(chooseState == JFileChooser.APPROVE_OPTION) {
					System.out.println("test");
				}
			}
		});
	}
	
	@Override
	public JPanel getTabPanel() {
		return ui.getTabPanel();
	}

	@Override
	public String getTabName() {
		return "FilechooserTab";
	}

}
