package testEditor.frontend.persistenceToolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
				save();
			}

		});
		ui.getSaveAs().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAs();
			}
		});
		ui.getLoad().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
	}

	private void save() {
		if(delegate.hasFile()) {
			delegate.save();
		}
		else {
			saveAs();
		}
	}
	private void saveAs() {
		File file = acceptFile();
		if(file != null) {
			delegate.saveAs(file);
		}
	}
	private void load() {
		File file = acceptFile();
		if(file != null) {
			delegate.load(file);
		}
	}

	private File acceptFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setName("persistence.filechooser");
		chooser.setFileFilter(new HtmlFileFilter());
		int returnValue = chooser.showOpenDialog(ui.getComponent());
		File file = null;
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();
		}
		return file;
	}
	public void setPersistenceToolbarDelegate(PersistenceToolbarDelegate delegate) {
		this.delegate = delegate;
	}
	
	public JComponent getComponent() {
		return ui.getComponent();
	}
}
