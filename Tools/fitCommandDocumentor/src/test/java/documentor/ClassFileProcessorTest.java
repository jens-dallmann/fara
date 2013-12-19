package documentor;

import material.HasOneCommand;
import material.MoreThanOneCommand;
import material.WithoutAnyCommand;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClassFileProcessorTest {

    @Test
    public void testProcess() throws Exception {
        ClassFileProcessor processor = new ClassFileProcessor();
        List<FitCommandDoc> process = processor.process(HasOneCommand.class);
        assertEquals(1, process.size());
        FitCommandDoc oneMethodDoc = process.get(0);
        assertEquals("HasOneCommand", oneMethodDoc.getClassName());
        assertEquals("oneMethod", oneMethodDoc.getCommandName());
        List<String> commandParams = oneMethodDoc.getCommandParams();
        assertEquals(1, commandParams.size());
        assertEquals("Parameter 1", commandParams.get(0));
    }

    @Test
    public void testProcessMoreThenOneCommand() throws Exception {
        ClassFileProcessor processor = new ClassFileProcessor();
        List<FitCommandDoc> process = processor.process(MoreThanOneCommand.class);
        assertEquals(3, process.size());

        //check first FitCommandDoc
        FitCommandDoc oneMethodDoc = process.get(0);
        assertEquals("MoreThanOneCommand", oneMethodDoc.getClassName());
        assertEquals("firstMethod", oneMethodDoc.getCommandName());

        List<String> commandParams = oneMethodDoc.getCommandParams();
        assertEquals(1, commandParams.size());
        assertEquals("Parameter 1", commandParams.get(0));

        //check second FitCommandDoc
        FitCommandDoc secondMethodDoc = process.get(1);
        assertEquals("MoreThanOneCommand", secondMethodDoc.getClassName());
        assertEquals("secondMethod", secondMethodDoc.getCommandName());

        List<String> secondCommandParams = secondMethodDoc.getCommandParams();
        assertEquals(2, secondCommandParams.size());
        assertEquals("Parameter 1", secondCommandParams.get(0));
        assertEquals("Parameter 2", secondCommandParams.get(1));


        //check third FitCommandDoc
        FitCommandDoc thirdMethodDoc = process.get(2);
        assertEquals("MoreThanOneCommand", thirdMethodDoc.getClassName());
        assertEquals("thirdMethod", thirdMethodDoc.getCommandName());

        List<String> thirdCommandParams = thirdMethodDoc.getCommandParams();
        assertEquals(0, thirdCommandParams.size());
    }

    @Test
    public void testProcessWithoutCommands() throws Exception {
        ClassFileProcessor processor = new ClassFileProcessor();
        List<FitCommandDoc> process = processor.process(WithoutAnyCommand.class);
        assertEquals(0, process.size());
    }
}
