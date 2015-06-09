package runOneTest;

import fitArchitectureAdapter.AbstractActionFixtureAggregator;
import fitArchitectureAdapter.interfaces.HasCommands;
import fitArchitectureAdapter.container.CommandResult;
import fitArchitectureAdapter.annotations.FitCommand;

import java.lang.Override;

public class FixtureAction extends AbstractActionFixtureAggregator {

  public FixtureAction() {
    init();
  }

  @Override
  public void addFixtureObjects() {
    addCommandObject(new AnyCommand());
  }
}