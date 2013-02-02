package testEditor.frontend.toolbar;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolbarUI {
	private JPanel panel;
	private JButton nextStep;
	private JButton play;
	private JButton skip;
	
	public ToolbarUI() {
		panel = new JPanel();
		nextStep = new JButton("Step");
		play = new JButton("Play");
		skip = new JButton("Skip");
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
	}
}
