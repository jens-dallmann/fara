package processableTable;

import java.util.ArrayList;
import java.util.List;

public class ProcessTableObservable {
  private List<ProcessTableListener> listeners;

  public ProcessTableObservable() {
    listeners = new ArrayList<ProcessTableListener>();
  }

  public void registerListener(ProcessTableListener listener) {
    listeners.add(listener);
  }

  public void removeListener(ProcessTableListener listener) {
    listeners.remove(listener);
  }

  protected void informDoNextStep() {
    for (ProcessTableListener listener : listeners) {
      listener.doNextStep();
    }
  }
}
