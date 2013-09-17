package fest.swing.operators;

import java.util.HashMap;
import java.util.Map;

public class RelationalOperatorDispatcher {
  private Map<String, RelationalOperator> operators;

  public RelationalOperatorDispatcher() {
    operators = new HashMap<String, RelationalOperator>();
    operators.put("=", new EqualsRelationalOperator());
  }

  public RelationalOperator findOperator(String operator) {
    return operators.get(operator);
  }
}
