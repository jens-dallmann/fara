package textComponentTab;

import javax.swing.JPanel;

import interfaces.TabController;

public class TextComponentTabController implements TabController{

	private TextComponentTabUI ui;

	public TextComponentTabController() {
		ui = new TextComponentTabUI();
	}

	@Override
	public JPanel getTabPanel() {
		return ui.getTabPanel();
	}

	@Override
	public String getTabName() {
		return "Text Component";
	}
}
