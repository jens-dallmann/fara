package core.service;

import core.service.exceptions.CreateFileException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class FitIOServiceTest {

  private FileService service;

  @Before
  public void setup() {
    service = new FileService();
  }

  @Test
  public void testCreateFileIfNotExist() throws CreateFileException, IOException {
    File created = service.createFileIfNotExist("temp.fit");
    assertTrue(created.exists());
    created.delete();

    File beforeCreatedFile = new File("temp.fit");
    beforeCreatedFile.createNewFile();
    created = service.createFileIfNotExist(beforeCreatedFile);
    assertTrue(created.exists());
    beforeCreatedFile.delete();
    created.delete();

    created = service.createFileIfNotExist(new File("temp.fit"));
    assertTrue(created.exists());
    created.delete();
  }
}
