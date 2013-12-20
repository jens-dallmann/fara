package textComponentTab;

import interfaces.TabController;

import javax.swing.*;

public class TextComponentTabController implements TabController {

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
