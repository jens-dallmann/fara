package core.command;

import javax.swing.*;

public interface TableCommand extends Command {

  void setTable(JTable table);

  JTable getTable();

}
