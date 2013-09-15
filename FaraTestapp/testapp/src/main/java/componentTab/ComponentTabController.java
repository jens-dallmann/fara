package componentTab;

import interfaces.TabController;

import javax.swing.JPanel;

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
