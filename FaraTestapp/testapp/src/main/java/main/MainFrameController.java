package main;

import componentTab.ComponentTabController;
import filechooserTab.FilechooserTabController;
import interfaces.TabController;
import labelTab.LabelTabController;
import textComponentTab.TextComponentTabController;

import javax.swing.*;

public class MainFrameController {

  private MainFrameUI ui;

  public MainFrameController() {
    ui = new MainFrameUI();
    TabController textComponentTab = new TextComponentTabController();
    TabController componentTab = new ComponentTabController();
    TabController labelTab = new LabelTabController();
    TabController filechooserTab = new FilechooserTabController();
    ui.addTab(textComponentTab.getTabPanel(), textComponentTab.getTabName());
    ui.addTab(componentTab.getTabPanel(), componentTab.getTabName());
    ui.addTab(labelTab.getTabPanel(), labelTab.getTabName());
    ui.addTab(filechooserTab.getTabPanel(), filechooserTab.getTabName());
  }

  public JFrame getFrame() {
    return ui.getFrame();
  }
}
