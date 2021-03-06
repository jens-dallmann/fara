package fixture;

import fest.swing.*;
import fileCommands.FileCommands;
import fitArchitectureAdapter.AbstractProcessableFixtureAggregator;
import fitArchitectureAdapter.commands.GeneralCommands;
import htmlCommands.HTMLCommands;

public class TestEditorAggregator extends AbstractProcessableFixtureAggregator {
  private SwingFrameWrapper wrapper;

  public TestEditorAggregator() {
    this.wrapper = new SwingFrameWrapper();
    init();
  }

  @Override
  public void addFixtureObjects() {
    addCommandObject(new SwingFileChooserAdapter(wrapper));
    addCommandObject(new SwingButtonUIAdapter(wrapper));
    addCommandObject(new SwingTextComponentUIAdapter(wrapper));
    addCommandObject(new SwingTabbedPaneUIAdapter(wrapper));
    addCommandObject(new SwingComponentUIAdapter(wrapper));
    addCommandObject(new SwingLabelUIAdapter(wrapper));
    addCommandObject(new TestEditorFixture(wrapper));
    addCommandObject(new SwingTableUIAdapter(wrapper));
    addCommandObject(new GeneralCommands());
    addCommandObject(new HTMLCommands());
    addCommandObject(new FileCommands());
  }
}
