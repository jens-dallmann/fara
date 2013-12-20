package core;

import directoryCrawler.CrawlerAction;
import directoryCrawler.DirectoryCrawler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ClassLoaderUtilsTest {

  @Mock
  private DirectoryCrawler crawler;

  @Before
  public void setUp() {
    initMocks(this);
  }

  @Test
  public void testLoadClassesRecursivelyFromDirectory() throws Exception {
    File file = mock(File.class);
    List<Class<?>> classes = ClassLoaderUtils.loadClassesRecursivelyFromDirectory(crawler, mock(ClassLoader.class), file);
    verify(crawler, times(1)).crawlDirectory(eq(file), Mockito.isNull(CrawlerAction.class), any(LoadClassCrawlerAction.class), eq(true));
    assertTrue(classes != null);
    assertEquals(0, classes.size());
  }
}
