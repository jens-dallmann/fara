package core.command;

import javax.swing.JTable;

public interface TableCommand extends Command{

	void setTable(JTable table);

	JTable getTable();
	
}
