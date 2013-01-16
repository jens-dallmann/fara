package main;

import interfaces.TabController;

import javax.swing.JFrame;

import labelTab.LabelTabController;
import textComponentTab.TextComponentTabController;

import componentTab.ComponentTabController;

public class MainFrameController {

	private MainFrameUI ui;

	public MainFrameController() {
		ui = new MainFrameUI();
		TabController textComponentTab = new TextComponentTabController();
		TabController componentTab = new ComponentTabController();
		TabController labelTab = new LabelTabController();
		ui.addTab(textComponentTab.getTabPanel(), textComponentTab.getTabName());
		ui.addTab(componentTab.getTabPanel(), componentTab.getTabName());
		ui.addTab(labelTab.getTabPanel(), labelTab.getTabName());
	}
	
	public JFrame getFrame() {
		return ui.getFrame();
	}
}
