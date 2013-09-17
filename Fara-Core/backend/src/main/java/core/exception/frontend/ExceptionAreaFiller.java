package core.exception.frontend;

import javax.swing.JTextArea;

public interface ExceptionAreaFiller {
  public void fillArea(Throwable throwable, JTextArea area);
}
