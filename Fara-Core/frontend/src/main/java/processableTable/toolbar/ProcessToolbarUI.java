package processableTable.toolbar;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ProcessToolbarUI {
  private JPanel panel;
  private JButton nextStep;
  private JButton play;
  private JButton skip;

  public ProcessToolbarUI() {
    panel = new JPanel();

    nextStep = new JButton("Step");
    nextStep.setName("step");

    play = new JButton("Play");
    play.setName("play");

    skip = new JButton("Skip");
    skip.setName("skip");

    panel.add(nextStep);
    panel.add(play);
    panel.add(skip);
  }

  public JButton getSkipButton() {
    return skip;
  }

  public JButton getNextStepButton() {
    return nextStep;
  }

  public JButton getPlayButton() {
    return play;
  }

  public JPanel getPanel() {
    return panel;
  }

  public void setButtonsEnabled(boolean state) {
    nextStep.setEnabled(state);
    play.setEnabled(state);
    skip.setEnabled(state);
  }
}
