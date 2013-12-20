package core.exception.frontend;

import core.ResourceLoader;

import javax.swing.*;
import java.awt.*;

public class ExceptionWindowController {
  private ExceptionWindowUI ui;

  public ExceptionWindowController(Window parent, Throwable exception, ExceptionLevel errorLevel, ExceptionAreaFiller areaFiller) {
    String captionText = exception.getMessage();
    if (captionText == null) {
      captionText = exception.getClass().getName();
    }
    ui = new ExceptionWindowUI(parent, createImageIcon(errorLevel), captionText);
    areaFiller.fillArea(exception, ui.getArea());
    ui.getDialog().setModal(true);
    ui.getDialog().setVisible(true);
  }

  private ImageIcon createImageIcon(ExceptionLevel errorLevel) {
    if (errorLevel == ExceptionLevel.ERROR) {
      return ResourceLoader.getSmoothImageIconResized(ResourceLoader.ERROR_ICON, 60, 60);
    } else if (errorLevel == ExceptionLevel.WARNING) {
      return ResourceLoader.getSmoothImageIconResized(ResourceLoader.WARNING_ICON, 60, 60);
    } else if (errorLevel == ExceptionLevel.INFO) {
      return ResourceLoader.getSmoothImageIconResized(ResourceLoader.INFO_ICON, 60, 60);
    }
    return null;
  }
}
