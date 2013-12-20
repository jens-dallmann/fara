package core.command.supports;

import core.command.Command;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.event.KeyEvent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: Deception
 * Date: 04.08.13
 * Time: 12:41
 * To change this template use File | Settings | File Templates.
 */
public class KeyEventExecutorImplTest {
  @Mock
  private UndoRedoSupport undoRedoSupport;

  @Mock
  private CommandFactory commandFactory;

  private KeyEventExecutor uiUndoRedoSupport;

  @Before
  public void beforeTest() {
    MockitoAnnotations.initMocks(this);
    uiUndoRedoSupport = new KeyEventExecutorImpl(commandFactory, undoRedoSupport);
  }

  @Test
  public void executeUndoEventSuccess() {
    KeyEvent event = getConfiguredKeyEvent(KeyEvent.VK_Z);
    when(undoRedoSupport.undo()).thenReturn(true);

    boolean execute = uiUndoRedoSupport.execute(event);

    verifyNoCommandHasBeenCollected();
    verifyUndoEventDone();
    verifyRedoNotDone();
    assertTrue(execute);
  }

  @Test
  public void executeUndoEventFails() {
    KeyEvent event = getConfiguredKeyEvent(KeyEvent.VK_Z);
    when(undoRedoSupport.undo()).thenReturn(false);

    boolean execute = uiUndoRedoSupport.execute(event);

    verifyNoCommandHasBeenCollected();
    verifyUndoEventDone();
    verifyRedoNotDone();
    assertFalse(execute);
  }


  @Test
  public void executeRedoEventSuccess() {
    KeyEvent event = getConfiguredKeyEvent(KeyEvent.VK_Y);
    when(undoRedoSupport.redo()).thenReturn(true);

    boolean execute = uiUndoRedoSupport.execute(event);

    verifyNoCommandHasBeenCollected();
    verifyUndoNotDone();
    verifyRedoDone();
    assertTrue(execute);
  }

  @Test
  public void executeRedoEventFails() {
    KeyEvent event = getConfiguredKeyEvent(KeyEvent.VK_Y);
    when(undoRedoSupport.redo()).thenReturn(false);

    boolean execute = uiUndoRedoSupport.execute(event);

    verifyNoCommandHasBeenCollected();
    verifyUndoNotDone();
    verifyRedoDone();
    assertFalse(execute);
  }

  @Test
  public void executeCommandNoCommandFound() {
    KeyEvent event = getConfiguredKeyEvent(KeyEvent.KEY_FIRST);
    when(commandFactory.getCommand(event)).thenReturn(null);
    boolean execute = uiUndoRedoSupport.execute(event);

    verifyUndoNotDone();
    verifyRedoNotDone();
    verifyCommandRequested(event);
    verifyNoCommandExecuted();
    assertFalse(execute);
  }

  @Test
  public void executeCommandCommandFoundNoSuccess() {
    KeyEvent event = getConfiguredKeyEvent(KeyEvent.KEY_FIRST);
    Command command = mock(Command.class);
    when(undoRedoSupport.execute(command)).thenReturn(false);
    when(commandFactory.getCommand(event)).thenReturn(command);

    boolean execute = uiUndoRedoSupport.execute(event);

    verifyUndoNotDone();
    verifyRedoNotDone();
    verifyCommandRequested(event);
    verifyExecuteCommand(command);
    assertFalse(execute);
  }

  @Test
  public void executeCommandCommandSuccessfullExecuted() {
    KeyEvent event = getConfiguredKeyEvent(KeyEvent.KEY_FIRST);
    Command command = mock(Command.class);
    when(undoRedoSupport.execute(command)).thenReturn(true);
    when(commandFactory.getCommand(event)).thenReturn(command);

    boolean execute = uiUndoRedoSupport.execute(event);

    verifyUndoNotDone();
    verifyRedoNotDone();
    verifyCommandRequested(event);
    verifyExecuteCommand(command);
    assertTrue(execute);
  }

  private void verifyExecuteCommand(Command command) {
    verify(undoRedoSupport, times(1)).execute(command);
  }

  private void verifyNoCommandExecuted() {
    verify(undoRedoSupport, times(0)).execute(any(Command.class));
  }

  private void verifyCommandRequested(KeyEvent event) {
    verify(commandFactory, times(1)).getCommand(event);
  }

  private void verifyRedoDone() {
    verify(undoRedoSupport, times(1)).redo();
  }

  private void verifyUndoNotDone() {
    verify(undoRedoSupport, times(0)).undo();
  }

  private void verifyRedoNotDone() {
    verify(undoRedoSupport, times(0)).redo();
  }

  private void verifyUndoEventDone() {
    verify(undoRedoSupport, times(1)).undo();
  }

  private KeyEvent getConfiguredKeyEvent(int keyEvent) {
    KeyEvent event = mock(KeyEvent.class);
    when(event.getKeyCode()).thenReturn(keyEvent);
    return event;
  }

  private void verifyNoCommandHasBeenCollected() {
    verify(commandFactory, times(0)).getCommand(any(KeyEvent.class));
  }
}
