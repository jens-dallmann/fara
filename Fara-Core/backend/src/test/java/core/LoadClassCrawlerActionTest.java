package core;

import directoryCrawler.ActionType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LoadClassCrawlerActionTest {

  private LoadClassCrawlerAction testling;

  @Mock
  private ClassLoader loader;

  @Mock
  private File input;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    testling = new LoadClassCrawlerAction(loader);
  }

  @Test
  public void testBeforeExecution() {
    List<Class<?>> result = testling.getResult();
    assertFalse(result == null);
    assertEquals(0, result.size());
  }

  @Test
  public void testExecuteResults() throws Exception {
    Class loadedClass = LoadClassCrawlerActionTest.class;
    when(input.getAbsolutePath()).thenReturn("a/b/c/classes/c/b/a.class");
    when(loader.loadClass("c.b.a")).thenReturn(loadedClass);

    testling.execute(input);
    List<Class<?>> result = testling.getResult();

    assertFalse(result == null);
    assertEquals(1,result.size());

    assertSame(loadedClass, result.get(0));
  }

  @Test
  public void testExecuteResultsClassNotFound() throws Exception {
    when(input.getAbsolutePath()).thenReturn("a/b/c/classes/c/b/a.class");
    when(loader.loadClass("c.b.a")).thenThrow(ClassNotFoundException.class);

    testling.execute(input);
    List<Class<?>> result = testling.getResult();

    assertFalse(result == null);
    assertEquals(0,result.size());
  }

  @Test(expected = AssertionError.class)
  public void testExecuteResultsWithWrongInput() throws Exception {
    when(input.getAbsolutePath()).thenReturn("any/separated/classes/String");
    testling.execute(input);
  }

  @Test
  public void testGetType() throws Exception {
    assertSame(ActionType.FILE, testling.getType());
  }

  @Test
  public void testGetName() throws Exception {
    assertEquals("core.LoadClassCrawlerAction", testling.getName());
  }

  @Test
  public void testIsExecutable() throws Exception {
    when(input.getAbsolutePath()).thenReturn("any.class");

    boolean actual = testling.isExecutable(input);

    assertTrue(actual);
  }

  @Test
  public void testIsNotExecutableInputIsNull() throws Exception {
    boolean actual = testling.isExecutable(null);

    assertFalse(actual);
  }

  @Test
  public void testIsNotExecutableIsNotAClass() throws Exception {
    when(input.getAbsolutePath()).thenReturn("any");

    boolean actual = testling.isExecutable(input);

    assertFalse(actual);
  }

  @Test
  public void testGetResult() throws Exception {

  }
}
