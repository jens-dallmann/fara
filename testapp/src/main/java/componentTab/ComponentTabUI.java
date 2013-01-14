package componentTab;

import interfaces.TabUI;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ComponentTabUI implements TabUI{

	private JPanel panel;
	private JComponent visibleComponent;
	private JComponent enabledComponent;
	private JComponent disabledComponent;
	
	public ComponentTabUI() {
		panel = new JPanel(new MigLayout("flowy","[grow, fill]"));
		visibleComponent = new JLabel("Visible Component");
		visibleComponent.setVisible(true);
		visibleComponent.setName("visibleComponent");
		
		enabledComponent = new JLabel("Enabled Component");
		enabledComponent.setName("enabledComponent");
		enabledComponent.setEnabled(true);
		
		disabledComponent = new JLabel("Disabled Component");
		disabledComponent.setName("disabledComponent");
		disabledComponent.setEnabled(false);
		
		panel.add(visibleComponent);
		panel.add(enabledComponent);
		panel.add(disabledComponent);
	}
	
	@Override
	public JPanel getTabPanel() {
		return panel;
	}
}
