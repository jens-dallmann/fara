package frontend.persistenceToolbar;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class PersistenceToolbarUI {
  private JButton save;
  private JButton saveAs;
  private JButton load;
  private JPanel panel;

  public PersistenceToolbarUI() {
    panel = new JPanel();
    panel.setLayout(new MigLayout());

    save = new JButton("Save");
    load = new JButton("Load");
    saveAs = new JButton("Save As");
    save.setName("persistenceToolbar.save");
    load.setName("persistenceToolbar.load");
    saveAs.setName("persistenceToolbar.saveAs");
    panel.add(save);
    panel.add(saveAs);
    panel.add(load);
  }

  public JButton getSave() {
    return save;
  }

  public JButton getSaveAs() {
    return saveAs;
  }

  public JButton getLoad() {
    return load;
  }

  public JComponent getComponent() {
    return panel;
  }
}
