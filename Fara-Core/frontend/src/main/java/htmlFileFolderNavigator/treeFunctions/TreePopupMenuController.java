package htmlFileFolderNavigator.treeFunctions;

import htmlFileFolderNavigator.utils.WrappedFile;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TreePopupMenuController {
  private final JFrame parent;
  private static TreePopupMenuUI ui;
  private JTree tree;

  public TreePopupMenuController(JFrame parent, JTree tree, Point locationOnScreen) {
    this.parent = parent;
    this.tree = tree;
    if (ui == null) {
      ui = new TreePopupMenuUI(locationOnScreen);
      addListeners();
    } else {
      ui.setNewLocation(locationOnScreen);
      ui.setVisible(true);
    }
  }

  private void addListeners() {
    ui.getCreateFolderItem().addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ui.closePopupmenu();
        createFolder();
      }
    });
  }

  private void createFolder() {
    String newName = retrieveNewName();
    DefaultMutableTreeNode lastPathComponent = extractSelectedNode();

    WrappedFile newFolder = createWrappedFile(newName, lastPathComponent);

    DefaultMutableTreeNode newBranchNode = new DefaultMutableTreeNode(newFolder);
    ((DefaultTreeModel) tree.getModel()).insertNodeInto(newBranchNode, lastPathComponent, lastPathComponent.getChildCount());

  }

  private DefaultMutableTreeNode extractSelectedNode() {
    TreePath selectedPath = tree.getSelectionPath();
    DefaultMutableTreeNode lastPathComponent = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
    return lastPathComponent;
  }

  private String retrieveNewName() {
    EditFolderController editFolderController = new EditFolderController(parent, "");
    String newName = editFolderController.getNewName();
    return newName;
  }

  private WrappedFile createWrappedFile(String newName,
                                        DefaultMutableTreeNode lastPathComponent) {
    WrappedFile userObject = (WrappedFile) lastPathComponent.getUserObject();
    String absolutePath = userObject.getFile().getAbsolutePath();
    String newAbsolutePath = absolutePath + File.separator + newName;
    File newFolder = new File(newAbsolutePath);
    newFolder.mkdir();
    return new WrappedFile(newFolder);
  }
}
