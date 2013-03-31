package fest.interfaces;

import fitArchitectureAdapter.container.CommandResult;

public interface TableUIAdapter {

	CommandResult checkTableRowCount(String tableName, String operator,
			String expected);
}
