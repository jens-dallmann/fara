package testEditor.frontend.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class ToolbarController{
	
	private ToolbarUI ui;
	
	private ToolbarDelegate toolbarDelegate;
	
	public ToolbarController(ToolbarDelegate toolbarDelegate) {
		ui = new ToolbarUI();
		registerListener();
		this.toolbarDelegate = toolbarDelegate;
	}

	private void registerListener() {
		ui.getNextStepButton().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				toolbarDelegate.nextStep();
			}
		});
		
		ui.getPlayButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toolbarDelegate.play();
			}
		});
		
		ui.getSkipButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				toolbarDelegate.skip();
			}
		});
	}
	
	public JPanel getPanel() {
		return ui.getPanel();
	}
	
	public void setButtonsEnabled(boolean state) {
		ui.setButtonsEnabled(state);
	}
}
