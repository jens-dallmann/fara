package componentTab;

import javax.swing.JPanel;

import interfaces.TabController;

public class ComponentTabController implements TabController {

	private ComponentTabUI ui;

	public ComponentTabController() {
		ui = new ComponentTabUI();
	}
	
	@Override
	public JPanel getTabPanel() {
		return ui.getTabPanel();
	}

	@Override
	public String getTabName() {
		return "Component Tab";
	}

}
