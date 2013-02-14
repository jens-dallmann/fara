package testEditor.frontend.editorTable;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class FitHtmlRepresentationUI {

	private JPanel tablePanel;
	private FixtureComponent fixtureComponent;

	public FitHtmlRepresentationUI(String fixture) {
		tablePanel = new JPanel();
		tablePanel.setLayout(new MigLayout("nogrid", "grow,fill"));
		fixtureComponent = new FixtureComponent();
		fixtureComponent.setTextFieldText(fixture);
		tablePanel.add(fixtureComponent.getPanel(), "wrap");
	}

	public JComponent getPanel() {
		return tablePanel;
	}
	
	public void addComponent(JComponent component) {
		tablePanel.add(component, "grow");
		tablePanel.revalidate();
	}

	public FixtureComponent getFixtureComponent() {
		return fixtureComponent;
	}
}
