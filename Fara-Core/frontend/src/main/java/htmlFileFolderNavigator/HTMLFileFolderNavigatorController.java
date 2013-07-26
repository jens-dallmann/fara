package htmlFileFolderNavigator;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import htmlFileFolderNavigator.treeFunctions.TreePopupMenuController;
import htmlFileFolderNavigator.utils.RootWrappedFile;
import htmlFileFolderNavigator.utils.WrappedFile;

public class HTMLFileFolderNavigatorController {
	private HTMLFileFolderNavigatorUI ui;
	private final JFrame parent;
	private DefaultTreeModel treeModel;

	public HTMLFileFolderNavigatorController(JFrame parent) {
		this.parent = parent;
		ui = new HTMLFileFolderNavigatorUI(treeModel);
		addListeners();
	}

	public void init(String rootFolder) {
		File rootFolderFile = new File(rootFolder);
		DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode();
		treeNode.setUserObject(new RootWrappedFile(rootFolderFile));
		fillBranchNode(treeNode, rootFolderFile);
		treeModel = new DefaultTreeModel(treeNode);
		ui.getTree().setModel(treeModel);
	}

	private void addListeners() {
		ui.getTree().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTree treeComponent = ui.getTree();
				int button = e.getButton();
				if (!treeComponent.isSelectionEmpty()
						&& button == MouseEvent.BUTTON3) {
					Point locationOnScreen = e.getLocationOnScreen();
					new TreePopupMenuController(parent, treeComponent,
							locationOnScreen);
				}
			}
		});
	}

	private void fillBranchNode(DefaultMutableTreeNode branchNode,
			File directory) {
		File[] listFiles = directory.listFiles(new HTMLFileFilter());
		if (listFiles != null) {
			for (File file : listFiles) {
				if (!(file.isDirectory() && file.getName().equals("result"))) {
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode();
					newNode.setUserObject(new WrappedFile(file));
					branchNode.add(newNode);
					if (file.isDirectory()) {
						newNode.setAllowsChildren(true);
						fillBranchNode(newNode, file);
					} else {
						newNode.setAllowsChildren(false);
					}
				}
			}
		}
	}

	public JComponent getScrollableTree() {
		return ui.getScrollableTree();
	}

	public void addSelectionListener(TreeSelectionListener selectionListener) {
		ui.getTree().addTreeSelectionListener(selectionListener);
	}
}
