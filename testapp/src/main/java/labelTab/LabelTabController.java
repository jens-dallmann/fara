package labelTab;

import interfaces.TabController;
import interfaces.TabUI;

import javax.swing.JPanel;

public class LabelTabController implements TabController {
	
	private TabUI ui;
	
	public LabelTabController() {
		ui = new LabelTabUI();
	}
	
	@Override
	public JPanel getTabPanel() {
		return ui.getTabPanel();
	}

	@Override
	public String getTabName() {
		return "labelTab";
	}
}
