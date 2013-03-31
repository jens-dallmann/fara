package fixture;

import fitArchitectureAdapter.AbstractProcessableFixtureAggregator;

public class DummyFixture extends AbstractProcessableFixtureAggregator{

	public DummyFixture() {
		super();
		init();
	}
	@Override
	public void addFixtureObjects() {
		addCommandObject(new DummyCommands());
	}
}
