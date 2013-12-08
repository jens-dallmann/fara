package directoryCrawler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class DirectoryCrawlerTest {

  private DirectoryCrawler crawler;

  @Mock
  private File testDirectory;

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);
    when(testDirectory.isDirectory()).thenReturn(true);
    crawler = new DirectoryCrawler();

  }

  @Test
  public void testCrawlDirectoryFileActionsExist() throws NoActionsToExecuteException {
    File file1 = mock(File.class);
    File file2 = mock(File.class);
    when(file1.isDirectory()).thenReturn(true);
    when(file2.isDirectory()).thenReturn(false);
    when(testDirectory.listFiles()).thenReturn(createDirectoryContent(file1, file2));

    CrawlerAction fileAction = mock(CrawlerAction.class);
    when(fileAction.isExecutable(file2)).thenReturn(true);
    when(fileAction.getName()).thenReturn("FirstDirectoryAction");
    when(fileAction.getType()).thenReturn(ActionType.DIRECTORY);

    List<CrawlerAction> fileActions = createCrawlerList(fileAction);
    crawler.crawlDirectory(testDirectory, null, fileActions, false);
    verify(fileAction, times(0)).isExecutable(file1);
    verify(fileAction, times(0)).execute(file1);
    verify(fileAction, times(1)).isExecutable(file2);
    verify(fileAction, times(1)).execute(file2);
  }

  @Test
  public void testCrawlDirectoryDirectoryActionsExist() throws Exception {
    File file1 = mock(File.class);
    File file2 = mock(File.class);
    when(file1.isDirectory()).thenReturn(true);
    when(file2.isDirectory()).thenReturn(false);
    when(testDirectory.listFiles()).thenReturn(createDirectoryContent(file1, file2));

    CrawlerAction directoryAction = mock(CrawlerAction.class);
    when(directoryAction.isExecutable(file1)).thenReturn(true);
    when(directoryAction.getName()).thenReturn("FirstDirectoryAction");
    when(directoryAction.getType()).thenReturn(ActionType.DIRECTORY);
    List<CrawlerAction> directoryActions = createCrawlerList(directoryAction);

    crawler.crawlDirectory(testDirectory, directoryActions, null, false);
    verify(directoryAction, times(1)).isExecutable(file1);
    verify(directoryAction, times(1)).execute(file1);
    verify(directoryAction, times(0)).isExecutable(file2);
    verify(directoryAction, times(0)).execute(file2);
  }

  @Test
  public void testCrawlDirectoryBothList() throws Exception {
    File file1 = mock(File.class);
    File file2 = mock(File.class);
    when(file1.getAbsolutePath()).thenReturn("File1");
    when(file2.getAbsolutePath()).thenReturn("File2");
    when(file1.isDirectory()).thenReturn(true);
    when(file2.isDirectory()).thenReturn(false);
    when(testDirectory.listFiles()).thenReturn(createDirectoryContent(file1, file2));

    CrawlerAction directoryAction = mock(CrawlerAction.class);
    when(directoryAction.isExecutable(file1)).thenReturn(true);
    when(directoryAction.getName()).thenReturn("FirstDirectoryAction");
    when(directoryAction.getType()).thenReturn(ActionType.DIRECTORY);

    CrawlerAction fileAction = mock(CrawlerAction.class);
    when(fileAction.isExecutable(file2)).thenReturn(true);
    when(fileAction.getName()).thenReturn("FirstFileAction");
    when(fileAction.getType()).thenReturn(ActionType.FILE);

    List<CrawlerAction> directoryActions = createCrawlerList(directoryAction);
    List<CrawlerAction> fileActions = createCrawlerList(fileAction);

    crawler.crawlDirectory(testDirectory, directoryActions, fileActions, false);
    verify(directoryAction, times(1)).isExecutable(file1);
    verify(directoryAction, times(1)).execute(file1);
    verify(directoryAction, times(0)).isExecutable(file2);
    verify(directoryAction, times(0)).execute(file2);

    verify(fileAction, times(0)).isExecutable(file1);
    verify(fileAction, times(0)).execute(file1);
    verify(fileAction, times(1)).isExecutable(file2);
    verify(fileAction, times(1)).execute(file2);
  }

  @Test
  public void testCrawlRecursivelyTrue() throws Exception {
    File file1 = mock(File.class);
    File file2 = mock(File.class);
    when(file1.isDirectory()).thenReturn(true);
    when(file2.isDirectory()).thenReturn(true);
    when(testDirectory.listFiles()).thenReturn(createDirectoryContent(file1));
    when(file1.listFiles()).thenReturn(createDirectoryContent(file2));

    List<CrawlerAction> actions = new ArrayList<CrawlerAction>();
    CrawlerAction action = mock(CrawlerAction.class);
    actions.add(action);

    crawler.crawlDirectory(testDirectory, actions, null, true);

    verify(file1, times(1)).listFiles();
    verify(action, times(1)).isExecutable(file1);
    verify(action, times(1)).isExecutable(file2);
  }

  @Test
  public void testCrawlRecursivelyFalse() throws Exception {
    File file1 = mock(File.class);
    File file2 = mock(File.class);
    when(file1.isDirectory()).thenReturn(true);
    when(file2.isDirectory()).thenReturn(true);
    when(testDirectory.listFiles()).thenReturn(createDirectoryContent(file1));
    when(file1.listFiles()).thenReturn(createDirectoryContent(file2));

    List<CrawlerAction> actions = new ArrayList<CrawlerAction>();
    CrawlerAction action = mock(CrawlerAction.class);
    actions.add(action);

    crawler.crawlDirectory(testDirectory, actions, null, false);

    verify(file1, times(0)).listFiles();
    verify(action, times(1)).isExecutable(file1);
    verify(action, times(0)).isExecutable(file2);
  }

  private List<CrawlerAction> createCrawlerList(CrawlerAction... actions) {
    List<CrawlerAction> crawlerList = new ArrayList<CrawlerAction>();
    for(CrawlerAction action: actions) {
      crawlerList.add(action);
    }
    return crawlerList;

  }

  public File[] createDirectoryContent(File... files) {
    return files;
  }
}
