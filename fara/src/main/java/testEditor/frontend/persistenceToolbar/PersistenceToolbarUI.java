package testEditor.frontend.persistenceToolbar;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class PersistenceToolbarUI {
	private JButton save;
	private JButton saveAs;
	private JButton load;
	private JPanel panel;
	
	public PersistenceToolbarUI() {
		panel = new JPanel();
		panel.setLayout(new MigLayout());
		
		save = new JButton("Save");
		load = new JButton("Load");
		saveAs = new JButton("Save As");
		
		panel.add(save);
		panel.add(saveAs);
		panel.add(load);
	}
	
	public JButton getSave() {
		return save;
	}
	
	public JButton getSaveAs() {
		return saveAs;
	}
	public JButton getLoad() {
		return load;
	}
	
	public JComponent getComponent() {
		return panel;
	}
}
