package labelTab;

import interfaces.TabUI;

import javax.swing.*;

public class LabelTabUI implements TabUI {

  private JPanel panel;
  private JLabel label;

  public LabelTabUI() {
    panel = new JPanel();
    label = new JLabel("Here is a text");
    label.setName("label");
    panel.add(label);
  }

  @Override
  public JPanel getTabPanel() {
    return panel;
  }
}
