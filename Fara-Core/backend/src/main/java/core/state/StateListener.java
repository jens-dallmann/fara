package core.state;

public interface StateListener<StateEnum extends Enum<?>> {

	void onStateChange(StateEnum newState);

}
