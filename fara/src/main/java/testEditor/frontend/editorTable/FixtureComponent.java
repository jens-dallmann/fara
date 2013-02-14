package testEditor.frontend.editorTable;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.lang.reflect.Modifier;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang3.StringUtils;

import fit.Fixture;

public class FixtureComponent {
	private JLabel label;
	private JTextField fixture;
	private JPanel panel;
	private FixtureChangedDelegate delegate;
	
	public FixtureComponent() {
		label = new JLabel("Fixture: ");
		fixture = new JTextField();
		panel = new JPanel();
		panel.setLayout(new MigLayout("align left", "[][grow, fill]"));
		panel.add(label);
		panel.add(fixture);
		fixture.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				Object component = e.getSource();
				if (component instanceof JTextField) {
					JTextField textfield = (JTextField) component;
					validateFixtureTextField(textfield.getText());
				}
			}
		});
	}
	
	public void setFixtureChangedDelegate(FixtureChangedDelegate delegate) {
		this.delegate = delegate;
	}
	public JPanel getPanel() {
		return panel;
	}

	private void validateFixtureTextField(String text) {
		if (StringUtils.isNotBlank(text)) {
			Class<?> fixtureClass = null;
			fixtureClass = tryToFindOnClassPath(text, fixtureClass);
			if (fixtureClass != null) {
				if (isFixture(fixtureClass)) {
					if(!isAbstract(fixtureClass)) {
						markFixtureTextFieldCorrect();
						triggerFixtureChanged();
					}
					else {
						markFixtureTextFieldWrong("Fixture Class is abstract and can not be instantiated from fit");
					}
				} else {
					markFixtureTextFieldWrong("Class is not a Fixture!");
				}
			}
		} else {
			resetFixtureTextField();
			triggerFixtureChanged();
		}
	}

	private boolean isAbstract(Class<?> fixtureClass) {
		return Modifier.isAbstract(fixtureClass.getModifiers());
	}

	private void triggerFixtureChanged() {
		if(delegate != null) {
			delegate.fixtureChanged(fixture.getText());
		}
	}

	private Class<?> tryToFindOnClassPath(String text, Class<?> fixtureClass) {
		try {
			fixtureClass = FitTableController.class.getClassLoader()
					.loadClass(text);
		} catch (ClassNotFoundException e) {
			markFixtureTextFieldWrong("class not found on classpath");
		}
		return fixtureClass;
	}

	private boolean isFixture(Class<?> fixtureClass) {
		return Fixture.class.isAssignableFrom(fixtureClass);
	}
	private void markFixtureTextFieldWrong(String string) {
		fixture.setToolTipText(string);
		fixture.setBorder(BorderFactory.createLineBorder(Color.RED));
	}

	private void markFixtureTextFieldCorrect() {
		fixture.setToolTipText(null);
		fixture.setBorder(BorderFactory.createLineBorder(Color.GREEN));
	}

	private void resetFixtureTextField() {
		Border border = new JTextField().getBorder();
		fixture.setToolTipText(null);
		fixture.setBorder(border);
	}

	public void setTextFieldText(String text) {
		fixture.setText(text);
		fixture.repaint();
		validateFixtureTextField(text);
	}
}