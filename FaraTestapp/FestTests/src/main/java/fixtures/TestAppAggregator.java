package fixtures;

import fest.swing.*;
import fitArchitectureAdapter.AbstractProcessableFixtureAggregator;


public class TestAppAggregator extends AbstractProcessableFixtureAggregator {

  private SwingFrameWrapper wrapper;

  public TestAppAggregator() {
    this.wrapper = new SwingFrameWrapper();
    init();
  }

  @Override
  public void addFixtureObjects() {
    addCommandObject(new SwingTextComponentUIAdapter(wrapper));
    addCommandObject(new SwingTabbedPaneUIAdapter(wrapper));
    addCommandObject(new SwingComponentUIAdapter(wrapper));
    addCommandObject(new SwingLabelUIAdapter(wrapper));
    addCommandObject(new TestAppFixture(wrapper));
    addCommandObject(new SwingFileChooserAdapter(wrapper));
    addCommandObject(new SwingButtonUIAdapter(wrapper));
  }
}
