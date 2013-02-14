package interfaces;

public interface StateCalculator<StateEnum extends Enum<?>> {
	public void calculateState();
	public StateEnum getState();
}
