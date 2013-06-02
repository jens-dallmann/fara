package testEditor.frontend.menubar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import testEditor.frontend.settings.TestSuiteSettingsController;
import testEditor.frontend.settings.TestSuiteSettingsListener;
import testEditor.frontend.shortcutListing.ShortcutListingController;

public class MenubarController {
	private MenubarUI ui;
	private TestSuiteSettingsListener testSuiteSettingsListener;
	
	public MenubarController(final JFrame frame) {
		ui = new MenubarUI();
		ui.getTableFunctionsItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ShortcutListingController();
			}
		});
		
		ui.getTestSuiteSettingsItem().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TestSuiteSettingsController(frame, testSuiteSettingsListener);
			}
		});
	}
	public void setTestSuiteSettingsListener(TestSuiteSettingsListener listener) {
		this.testSuiteSettingsListener = listener;
	}
	public JMenuBar getMenubar() {
		return ui.getMenuBar();
	}
}
