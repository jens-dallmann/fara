package frontend.shortcutListing;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ShortcutListingUI {
  private JDialog dialog;

  public ShortcutListingUI(List<JPanel> shortcutCategories) {
    dialog = new JDialog();
    dialog.setTitle("Shortcut Listing");
    dialog.setLayout(new GridLayout(shortcutCategories.size(), 1));
    for (JPanel oneCategory : shortcutCategories) {
      dialog.add(oneCategory);
    }
    dialog.setSize(400, 400);
    dialog.setVisible(true);
  }
}
