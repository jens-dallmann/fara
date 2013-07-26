package frontend.menubar;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenubarUI {

	private JMenuBar menubar;
	private JMenuItem tableFunctions;
	private JMenu settingsMenu;
	private JMenuItem testSuiteSettings;
	
	public MenubarUI() {
		menubar = new JMenuBar();
		settingsMenu = new JMenu("Settings");
		testSuiteSettings = new JMenuItem("TestSuite");
		settingsMenu.add(testSuiteSettings);
		tableFunctions = new JMenuItem("Table Functions");
		menubar.add(settingsMenu);
		menubar.add(tableFunctions);
		
	}
	
	public JMenuBar getMenuBar() {
		return menubar;
	}
	
	public JMenuItem getTableFunctionsItem() {
		return tableFunctions;
	}
	
	public JMenuItem getTestSuiteSettingsItem() {
		return testSuiteSettings;
	}
}
