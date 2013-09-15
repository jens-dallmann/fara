package textComponentTab;

import interfaces.TabUI;
import net.miginfocom.swing.MigLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class TextComponentTabUI implements TabUI {
  private JPanel panel;
  private JTextComponent notEditableComponent;
  private JTextComponent editableComponent;
  private JTextComponent checkTextComponent;
  private JTextComponent setTextComponent;

  public TextComponentTabUI() {
    panel = new JPanel(new MigLayout("", "[][fill, grow]"));
    setupNotEditableTextComponent();
    setupEditableTextComponent();
    setupCheckTextComponent();
    setupSetTextComponent();
    panel.add(new JLabel("NotEditableComponent"));
    panel.add(notEditableComponent, "wrap");
    panel.add(new JLabel("EditableComponent"));
    panel.add(editableComponent, "wrap");
    panel.add(new JLabel("CheckTextComponent"));
    panel.add(checkTextComponent, "wrap");
    panel.add(new JLabel("SetTextComponent"));
    panel.add(setTextComponent, "wrap");
  }

  private void setupSetTextComponent() {
    setTextComponent = new JTextField();
    setTextComponent.setName("setTextComponent");
  }

  private void setupCheckTextComponent() {
    checkTextComponent = new JTextField();
    checkTextComponent.setName("checkTextComponent");
    checkTextComponent.setText("checkTextComponent");
  }

  private void setupEditableTextComponent() {
    editableComponent = new JTextField();
    editableComponent.setName("editableComponent");
  }

  private void setupNotEditableTextComponent() {
    notEditableComponent = new JTextField();
    notEditableComponent.setEditable(false);
    notEditableComponent.setName("notEditableComponent");
  }

  public JPanel getTabPanel() {
    return panel;
  }
}