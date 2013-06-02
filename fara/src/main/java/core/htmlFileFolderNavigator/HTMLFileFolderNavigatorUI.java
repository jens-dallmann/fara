package core.htmlFileFolderNavigator;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

public class HTMLFileFolderNavigatorUI {
	private JTree tree;
	
	public HTMLFileFolderNavigatorUI(DefaultTreeModel treeNode) {
		tree = new JTree(treeNode);
	}
	
	public JComponent getScrollableTree() {
		return new JScrollPane(tree);
	}
	
	public JTree getTree() {
		return tree;
	}
}
