package fixtures;
import fest.swing.SwingComponentUIAdapter;
import fest.swing.SwingFrameWrapper;
import fest.swing.SwingTabbedPaneUIAdapter;
import fest.swing.SwingTextComponentUIAdapter;
import fitArchitectureAdapter.AbstractActionFixtureAggregator;


public class TestAppAggregator extends AbstractActionFixtureAggregator{

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
		addCommandObject(new TestAppFixture(wrapper));
	}
}
