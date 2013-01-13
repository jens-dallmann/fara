package main;

import interfaces.TabController;

import javax.swing.JFrame;

import componentTab.ComponentTabController;

import textComponentTab.TextComponentTabController;

public class MainFrameController {

	private MainFrameUI ui;

	public MainFrameController() {
		ui = new MainFrameUI();
		TabController textComponentTab = new TextComponentTabController();
		TabController componentTab = new ComponentTabController();
		ui.addTab(textComponentTab.getTabPanel(), textComponentTab.getTabName());
		ui.addTab(componentTab.getTabPanel(), componentTab.getTabName());
	}
	
	public JFrame getFrame() {
		return ui.getFrame();
	}
}
