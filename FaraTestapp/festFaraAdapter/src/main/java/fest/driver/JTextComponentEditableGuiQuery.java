package fest.driver;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

import javax.swing.text.JTextComponent;

import static org.fest.swing.edt.GuiActionRunner.execute;

public class JTextComponentEditableGuiQuery {
  @RunsInEDT
  public static boolean isEditable(final JTextComponent textComponent) {
    return execute(new GuiQuery<Boolean>() {
      public Boolean executeInEDT() {
        return textComponent.isEditable();
      }
    });
  }
}
