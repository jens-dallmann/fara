package frontend.shortcutListing.category;

import net.miginfocom.swing.MigLayout;

import javax.swing.JPanel;
import java.util.List;

public class ShortcutCategoryController {

  private ShortcutCategoryUI ui;

  public ShortcutCategoryController(String title, List<ShortcutEntry> entries) {
    JPanel shortcutPanel = buildShortcutPanel(entries);
    ui = new ShortcutCategoryUI(title, shortcutPanel);
  }

  private JPanel buildShortcutPanel(List<ShortcutEntry> entries) {
    JPanel shortcutPanel = new JPanel();
    shortcutPanel.setLayout(new MigLayout());
    for (ShortcutEntry oneEntry : entries) {
      shortcutPanel.add(oneEntry.getEntry(), "wrap");
    }
    return shortcutPanel;
  }

  public JPanel getShortcutCategory() {
    return ui.getPanel();
  }
}
