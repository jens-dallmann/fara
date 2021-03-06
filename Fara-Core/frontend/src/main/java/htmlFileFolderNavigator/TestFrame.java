package htmlFileFolderNavigator;

import javax.swing.*;

public class TestFrame {
  public TestFrame() {
    JFrame frame = new JFrame();
    frame.setSize(500, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    HTMLFileFolderNavigatorController folderTree = new HTMLFileFolderNavigatorController(frame);
    folderTree.init("C:\\Users\\Deception\\Downloads");
    JComponent scrollableTree = folderTree.getScrollableTree();
    frame.add(scrollableTree);
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    new TestFrame();
  }
}
