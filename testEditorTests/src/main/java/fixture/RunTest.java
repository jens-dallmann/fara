package fixture;
import testEditor.frontend.StartTestEditor;


public class RunTest {
	public static void main(String[] args) {
		String testfile = "C:\\data\\fara\\trunk\\testEditorTests\\src\\test\\resources\\StartTestEditor.html";
		new StartTestEditor().start(new String[]{testfile});
	}
}
