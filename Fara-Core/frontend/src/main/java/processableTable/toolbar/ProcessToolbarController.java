package processableTable.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;



public class ProcessToolbarController{
	
	private ProcessToolbarUI ui;
	
	private ProcessToolbarDelegate processToolbarDelegate;
	
	public ProcessToolbarController() {
		ui = new ProcessToolbarUI();
		registerListener();
	}

	public void setProcessToolbarDelegate(ProcessToolbarDelegate processToolbarDelegate) {
		this.processToolbarDelegate = processToolbarDelegate;
	}
	private void registerListener() {
		ui.getNextStepButton().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				processToolbarDelegate.nextStep();
			}
		});
		
		ui.getPlayButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processToolbarDelegate.play();
			}
		});
		
		ui.getSkipButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				processToolbarDelegate.skip();
			}
		});
	}
	
	public JComponent getComponent() {
		return ui.getPanel();
	}
	
	public void setButtonsEnabled(boolean state) {
		ui.setButtonsEnabled(state);
	}
}
