package testEditor.frontend.toolbar;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolbarUI {
	private JPanel panel;
	private JButton nextStep;
	private JButton play;
	private JButton skip;
	private JButton save;
	
	public ToolbarUI() {
		panel = new JPanel();
		
		nextStep = new JButton("Step");
		nextStep.setName("step");
		
		play = new JButton("Play");
		play.setName("play");
		
		skip = new JButton("Skip");
		skip.setName("skip");
		
		save = new JButton("Save");
		save.setName("save");
		
		panel.add(nextStep);
		panel.add(play);
		panel.add(skip);
		panel.add(save);
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
	
	public JButton getSaveButton() {
		return save;
	}

	public JPanel getPanel() {
		return panel;
	}
	
	public void setButtonsEnabled(boolean state) {
		nextStep.setEnabled(state);
		play.setEnabled(state);
		skip.setEnabled(state);
		save.setEnabled(state);
	}
}
