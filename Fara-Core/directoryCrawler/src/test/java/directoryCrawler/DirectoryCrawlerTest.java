package directoryCrawler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryCrawlerTest {

  private DirectoryCrawler crawler;

  @Mock
  private File testDirectory;

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);
    Mockito.when(testDirectory.isDirectory()).thenReturn(true);
    crawler = new DirectoryCrawler();
  }

  @Test
  public void testCrawlDirectoryFileActionsExist() throws NoActionsToExecuteException {
    File file1 = Mockito.mock(File.class);
    File file2 = Mockito.mock(File.class);
    Mockito.when(file1.isDirectory()).thenReturn(true);
    Mockito.when(file2.isDirectory()).thenReturn(false);
    Mockito.when(testDirectory.listFiles()).thenReturn(createDirectoryContent(file1, file2));

    CrawlerAction fileAction = Mockito.mock(CrawlerAction.class);
    Mockito.when(fileAction.isExecutable(file2)).thenReturn(true);
    Mockito.when(fileAction.getName()).thenReturn("FirstDirectoryAction");
    Mockito.when(fileAction.getType()).thenReturn(ActionType.DIRECTORY);

    List<CrawlerAction> fileActions = createCrawlerList(fileAction);
    crawler.crawlDirectory(testDirectory, null, fileActions, false);
    Mockito.verify(fileAction, Mockito.times(0)).isExecutable(file1);
    Mockito.verify(fileAction, Mockito.times(0)).execute(file1);
    Mockito.verify(fileAction, Mockito.times(1)).isExecutable(file2);
    Mockito.verify(fileAction, Mockito.times(1)).execute(file2);
  }

  @Test
  public void testCrawlDirectoryDirectoryActionsExist() throws Exception {
    File file1 = Mockito.mock(File.class);
    File file2 = Mockito.mock(File.class);
    Mockito.when(file1.isDirectory()).thenReturn(true);
    Mockito.when(file2.isDirectory()).thenReturn(false);
    Mockito.when(testDirectory.listFiles()).thenReturn(createDirectoryContent(file1, file2));

    CrawlerAction directoryAction = Mockito.mock(CrawlerAction.class);
    Mockito.when(directoryAction.isExecutable(file1)).thenReturn(true);
    Mockito.when(directoryAction.getName()).thenReturn("FirstDirectoryAction");
    Mockito.when(directoryAction.getType()).thenReturn(ActionType.DIRECTORY);
    List<CrawlerAction> directoryActions = createCrawlerList(directoryAction);

    crawler.crawlDirectory(testDirectory, directoryActions, null, false);
    Mockito.verify(directoryAction, Mockito.times(1)).isExecutable(file1);
    Mockito.verify(directoryAction, Mockito.times(1)).execute(file1);
    Mockito.verify(directoryAction, Mockito.times(0)).isExecutable(file2);
    Mockito.verify(directoryAction, Mockito.times(0)).execute(file2);
  }

  @Test
  public void testCrawlDirectoryBothList() throws Exception {
    File file1 = Mockito.mock(File.class);
    File file2 = Mockito.mock(File.class);
    Mockito.when(file1.getAbsolutePath()).thenReturn("File1");
    Mockito.when(file2.getAbsolutePath()).thenReturn("File2");
    Mockito.when(file1.isDirectory()).thenReturn(true);
    Mockito.when(file2.isDirectory()).thenReturn(false);
    Mockito.when(testDirectory.listFiles()).thenReturn(createDirectoryContent(file1, file2));

    CrawlerAction directoryAction = Mockito.mock(CrawlerAction.class);
    Mockito.when(directoryAction.isExecutable(file1)).thenReturn(true);
    Mockito.when(directoryAction.getName()).thenReturn("FirstDirectoryAction");
    Mockito.when(directoryAction.getType()).thenReturn(ActionType.DIRECTORY);

    CrawlerAction fileAction = Mockito.mock(CrawlerAction.class);
    Mockito.when(fileAction.isExecutable(file2)).thenReturn(true);
    Mockito.when(fileAction.getName()).thenReturn("FirstFileAction");
    Mockito.when(fileAction.getType()).thenReturn(ActionType.FILE);

    List<CrawlerAction> directoryActions = createCrawlerList(directoryAction);
    List<CrawlerAction> fileActions = createCrawlerList(fileAction);

    crawler.crawlDirectory(testDirectory, directoryActions, fileActions, false);
    Mockito.verify(directoryAction, Mockito.times(1)).isExecutable(file1);
    Mockito.verify(directoryAction, Mockito.times(1)).execute(file1);
    Mockito.verify(directoryAction, Mockito.times(0)).isExecutable(file2);
    Mockito.verify(directoryAction, Mockito.times(0)).execute(file2);

    Mockito.verify(fileAction, Mockito.times(0)).isExecutable(file1);
    Mockito.verify(fileAction, Mockito.times(0)).execute(file1);
    Mockito.verify(fileAction, Mockito.times(1)).isExecutable(file2);
    Mockito.verify(fileAction, Mockito.times(1)).execute(file2);
  }

  @Test
  public void testCrawlRecursivelyTrue() throws Exception {
    File file1 = Mockito.mock(File.class);
    File file2 = Mockito.mock(File.class);
    Mockito.when(file1.isDirectory()).thenReturn(true);
    Mockito.when(file2.isDirectory()).thenReturn(true);
    Mockito.when(testDirectory.listFiles()).thenReturn(createDirectoryContent(file1));
    Mockito.when(file1.listFiles()).thenReturn(createDirectoryContent(file2));

    List<CrawlerAction> actions = new ArrayList<CrawlerAction>();
    CrawlerAction action = Mockito.mock(CrawlerAction.class);
    actions.add(action);

    crawler.crawlDirectory(testDirectory, actions, null, true);

    Mockito.verify(file1, Mockito.times(1)).listFiles();
    Mockito.verify(action, Mockito.times(1)).isExecutable(file1);
    Mockito.verify(action, Mockito.times(1)).isExecutable(file2);
  }

  @Test
  public void testCrawlRecursivelyFalse() throws Exception {
    File file1 = Mockito.mock(File.class);
    File file2 = Mockito.mock(File.class);
    Mockito.when(file1.isDirectory()).thenReturn(true);
    Mockito.when(file2.isDirectory()).thenReturn(true);
    Mockito.when(testDirectory.listFiles()).thenReturn(createDirectoryContent(file1));
    Mockito.when(file1.listFiles()).thenReturn(createDirectoryContent(file2));

    List<CrawlerAction> actions = new ArrayList<CrawlerAction>();
    CrawlerAction action = Mockito.mock(CrawlerAction.class);
    actions.add(action);

    crawler.crawlDirectory(testDirectory, actions, null, false);

    Mockito.verify(file1, Mockito.times(0)).listFiles();
    Mockito.verify(action, Mockito.times(1)).isExecutable(file1);
    Mockito.verify(action, Mockito.times(0)).isExecutable(file2);
  }

  private List<CrawlerAction> createCrawlerList(CrawlerAction... actions) {
    List<CrawlerAction> crawlerList = new ArrayList<CrawlerAction>();
    for (CrawlerAction action : actions) {
      crawlerList.add(action);
    }
    return crawlerList;

  }

  public File[] createDirectoryContent(File... files) {
    return files;
  }
}
