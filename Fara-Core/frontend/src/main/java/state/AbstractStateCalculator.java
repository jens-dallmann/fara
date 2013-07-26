package state;


import java.util.ArrayList;
import java.util.List;


public abstract class AbstractStateCalculator<StateEnum extends Enum<?>> implements StateCalculator<StateEnum>{
	private List<StateListener<StateEnum>> listeners;
	
	public AbstractStateCalculator() {
		listeners = new ArrayList<StateListener<StateEnum>>();
	}
	
	public void registerListener(StateListener<StateEnum> listener) {
		listeners.add(listener);
	}
	
	public void removeListener(StateListener<StateEnum> listener) {
		listeners.remove(listener);
	}
	
	public void fireStateChanged(StateEnum state) {
		for(StateListener<StateEnum> listener: listeners) {
			listener.onStateChange(state);
		}
	}
}
