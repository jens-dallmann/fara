package testEditor.frontend.shortcutListing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import testEditor.frontend.editorTable.ShortcutActionKeys;
import testEditor.frontend.shortcutListing.category.ShortcutCategoryController;
import testEditor.frontend.shortcutListing.category.ShortcutEntry;

public class ShortcutListingController {
	
	public ShortcutListingController() {
		List<JPanel> categories = new ArrayList<JPanel>();
		categories.add(buildTableFunctionCategory());
		new ShortcutListingUI(categories);
	}

	private JPanel buildTableFunctionCategory() {
		ShortcutActionKeys[] values = ShortcutActionKeys.values();
		List<ShortcutEntry> entries = new ArrayList<ShortcutEntry>();
		for(int i = 0; i < values.length; i++) {
			ShortcutActionKeys entry = values[i];
			boolean odd = false;
			if(i % 2 == 1) {
				odd = true;
			}
			entries.add(new ShortcutEntry(entry.getKey().toString(), entry.getDescription(), odd));
		}
		ShortcutCategoryController tablefunctionCategory = new ShortcutCategoryController("Table Functions", entries);
		return tablefunctionCategory.getShortcutCategory();
	}
	
}