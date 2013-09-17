package main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainFrameUI {

  private JFrame frame;
  private JTabbedPane tabbedPane;

  public MainFrameUI() {
    frame = new JFrame("Fest Fara Test App");
    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    tabbedPane = new JTabbedPane();
    tabbedPane.setName("MainTab");
    frame.add(tabbedPane);
    frame.setVisible(true);
  }

  public void addTab(JPanel panel, String title) {
    tabbedPane.add(panel, title);
  }

  public JFrame getFrame() {
    return frame;
  }
}
