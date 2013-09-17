package processableTable.table.model;


public class ProcessableRowStates {

  private boolean hasBreakpoint;
  private RowState state;
  private String failureMessage;

  public ProcessableRowStates() {
    state = RowState.INITIAL;
    hasBreakpoint = false;
    setFailureMessage("");
  }

  public void setState(RowState state) {
    this.state = state;
  }

  public RowState getState() {
    return state;
  }

  public void setHasBreakpoint(boolean hasBreakpoint) {
    this.hasBreakpoint = hasBreakpoint;
  }

  public boolean hasBreakpoint() {
    return hasBreakpoint;
  }

  public String getFailureMessage() {
    return failureMessage;
  }

  public void setFailureMessage(String failureMessage) {
    this.failureMessage = failureMessage;
  }

  public void toggleBreakpoint() {
    hasBreakpoint = !hasBreakpoint;
  }

}