package testEditor.frontend.persistenceToolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

public class PersistenceToolbarController {
	
	private PersistenceToolbarUI ui;
	private PersistenceToolbarDelegate delegate;
	
	public PersistenceToolbarController() {
		ui = new PersistenceToolbarUI();
		ui.getSave().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				delegate.save();
			}
		});
		ui.getLoad().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileFilter(new HtmlFileFilter());
				int returnValue = chooser.showOpenDialog(ui.getComponent());
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					delegate.load(chooser.getSelectedFile());
				}
			}
		});
	}
	
	public void setPersistenceToolbarDelegate(PersistenceToolbarDelegate delegate) {
		this.delegate = delegate;
	}
	
	public JComponent getComponent() {
		return ui.getComponent();
	}
}
