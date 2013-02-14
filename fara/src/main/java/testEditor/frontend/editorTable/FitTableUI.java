package testEditor.frontend.editorTable;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;

public class FitTableUI {

	private JPanel tablePanel;
	private JTable table;
	private JScrollPane scrollPanel;
	private FixtureComponent fixtureComponent;

	public FitTableUI(TableModel model, String fixture) {
		createTable(model);
		tablePanel = new JPanel();
		tablePanel.setLayout(new MigLayout("nogrid", "[grow,fill][grow,fill]"));
		fixtureComponent = new FixtureComponent();
		fixtureComponent.setTextFieldText(fixture);
		tablePanel.add(fixtureComponent.getPanel(), "wrap");
		tablePanel.add(scrollPanel, "grow");
	}

	private void createTable(TableModel model) {
		table = new JTable(model);
		scrollPanel = new JScrollPane(table);
		table.setDefaultRenderer(Object.class, new FitTableCellRenderer());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.getTableHeader().setReorderingAllowed(false);
	}

	public JComponent getPanel() {
		return tablePanel;
	}

	public JTable getTable() {
		return table;
	}

	public FixtureComponent getFixtureComponent() {
		return fixtureComponent;
	}
}
