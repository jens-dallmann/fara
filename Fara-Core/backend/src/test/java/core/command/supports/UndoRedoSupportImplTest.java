package core.command.supports;

import core.command.Command;
import core.command.CommandStack;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests the undo redo support
 */
public class UndoRedoSupportImplTest {

  @Mock
  private CommandStack executedStack;

  @Mock
  private CommandStack undoneStack;

  private UndoRedoSupport undoRedoSupport;

  @Before
  public void beforeTest() {
    MockitoAnnotations.initMocks(this);
    undoRedoSupport = new UndoRedoSupportImpl(executedStack, undoneStack);
  }

  @Test
  public void testCanUndo() {
    when(executedStack.isEmpty()).thenReturn(false);
    assertTrue(undoRedoSupport.canUndo());
  }

  @Test
  public void testCanNotUndo() {
    when(executedStack.isEmpty()).thenReturn(true);
    assertFalse(undoRedoSupport.canUndo());
  }

  @Test
  public void testCanRedo() {
    when(undoneStack.isEmpty()).thenReturn(false);
    assertTrue(undoRedoSupport.canRedo());
  }

  @Test
  public void testCanNotRedo() {
    when(undoneStack.isEmpty()).thenReturn(true);
    assertFalse(undoRedoSupport.canRedo());
  }

  @Test
  public void testExecuteWithSuccess() {
    Command command = mock(Command.class);
    when(command.execute()).thenReturn(true);

    boolean isExecuted = undoRedoSupport.execute(command);

    verify(command, times(1)).execute();
    verify(executedStack, times(0)).clear();
    verify(undoneStack, times(1)).clear();
    verify(executedStack, times(1)).push(command);
    assertTrue(isExecuted);
  }

  @Test
  public void testExecuteWithoutSuccess() {
    Command command = mock(Command.class);
    when(command.execute()).thenReturn(false);

    boolean isExecuted = undoRedoSupport.execute(command);

    verify(command, times(1)).execute();
    verify(executedStack, times(0)).clear();
    verify(undoneStack, times(0)).clear();
    verify(executedStack, times(0)).push(command);
    assertFalse(isExecuted);
  }

  @Test
  public void testUndoCanNotRedo() {
    when(executedStack.isEmpty()).thenReturn(true);

    boolean isUndone = undoRedoSupport.undo();

    verify(executedStack, times(0)).pop();
    verify(undoneStack, times(0)).push(any(Command.class));
    assertFalse(isUndone);
  }

  @Test
  public void testUndoUndoFailed() {
    Command command = mock(Command.class);
    when(executedStack.isEmpty()).thenReturn(false);
    when(executedStack.pop()).thenReturn(command);
    when(command.undo()).thenReturn(false);

    boolean isUndone = undoRedoSupport.undo();

    verify(executedStack, times(1)).pop();
    verify(command, times(1)).undo();
    verify(undoneStack, times(0)).push(any(Command.class));
    assertFalse(isUndone);
  }

  @Test
  public void testUndoSuccess() {
    Command command = mock(Command.class);
    when(executedStack.isEmpty()).thenReturn(false);
    when(executedStack.pop()).thenReturn(command);
    when(command.undo()).thenReturn(true);

    boolean isUndone = undoRedoSupport.undo();

    verify(executedStack, times(1)).pop();
    verify(command, times(1)).undo();
    verify(undoneStack, times(1)).push(command);
    assertTrue(isUndone);
  }

  @Test
  public void testRedoCanNotRedo() {
    when(undoneStack.isEmpty()).thenReturn(true);

    boolean isRedone = undoRedoSupport.redo();

    verify(undoneStack, times(0)).pop();
    assertFalse(isRedone);
  }

  @Test
  public void testRedoRedoFailed() {
    when(undoneStack.isEmpty()).thenReturn(false);
    Command command = mock(Command.class);
    when(undoneStack.pop()).thenReturn(command);
    when(command.execute()).thenReturn(false);

    boolean isRedone = undoRedoSupport.redo();

    verify(undoneStack, times(1)).pop();
    verify(command, times(1)).execute();
    verify(executedStack, times(0)).push(command);
    assertFalse(isRedone);
  }

  @Test
  public void testRedoRedoWithSuccess() {
    when(undoneStack.isEmpty()).thenReturn(false);
    Command command = mock(Command.class);
    when(undoneStack.pop()).thenReturn(command);
    when(command.execute()).thenReturn(true);

    boolean isRedone = undoRedoSupport.redo();

    verify(undoneStack, times(1)).pop();
    verify(command, times(1)).execute();
    verify(executedStack, times(1)).push(command);
    assertTrue(isRedone);
  }
}
