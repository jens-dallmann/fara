package frontend.editorTable;

import fit.Fixture;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.lang.reflect.Modifier;

public class FixtureComponent {
  private JLabel label;
  private JTextField fixture;
  private JPanel panel;
  private FixtureChangedDelegate delegate;

  public FixtureComponent() {
    label = new JLabel("Fixture: ");
    fixture = new JTextField();
    fixture.setName("fixture");
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
    Class<?> fixtureClass = null;
    if (StringUtils.isNotBlank(text)) {
      fixtureClass = tryToFindOnClassPath(text, fixtureClass);
      if (fixtureClass != null) {
        if (isFixture(fixtureClass)) {
          if (!isAbstract(fixtureClass)) {
            markFixtureTextFieldCorrect();
            triggerFixtureChanged(fixtureClass);
          } else {
            markFixtureTextFieldWrong("Fixture Class is abstract and can not be instantiated from fit");
          }
        } else {
          markFixtureTextFieldWrong("Class is not a Fixture!");
        }
      }
    } else {
      resetFixtureTextField();
      triggerFixtureChanged(fixtureClass);
    }
  }

  private boolean isAbstract(Class<?> fixtureClass) {
    return Modifier.isAbstract(fixtureClass.getModifiers());
  }

  private void triggerFixtureChanged(Class<?> newFixture) {
    if (delegate != null) {
      delegate.fixtureChanged(newFixture, fixture.getText());
    }
  }

  private Class<?> tryToFindOnClassPath(String text, Class<?> fixtureClass) {
    try {
      fixtureClass = FitHtmlRepresentationController.class.getClassLoader()
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

  public String getText() {
    return fixture.getText();
  }

  public boolean isValidFixture() {
    return false;
  }
}