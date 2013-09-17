package frontend.editorTable.tableFunctions.commands.model;

import core.command.CommandModel;
import fit.Parse;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class DeleteLineCommandModel implements CommandModel {
  private final int[] selectedRowsIndizes;
  private final Parse[] selectedRows;

  public DeleteLineCommandModel(final Parse[] selectedRows, final int[] selectedRowsIndizes) {
    this.selectedRows = Arrays.copyOf(selectedRows, selectedRows.length);
    this.selectedRowsIndizes = Arrays.copyOf(selectedRowsIndizes, selectedRowsIndizes.length);
  }

  public int[] getSelectedRowsIndizes() {
    return ArrayUtils.clone(selectedRowsIndizes);
  }

  public Parse[] getSelectedRows() {
    return ArrayUtils.clone(selectedRows);
  }
}
