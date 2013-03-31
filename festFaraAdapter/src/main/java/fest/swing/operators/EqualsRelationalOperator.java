package fest.swing.operators;

public class EqualsRelationalOperator implements RelationalOperator {

	@Override
	public boolean evaluate(double actual, double expected) {
		return actual == expected;
	}
}
