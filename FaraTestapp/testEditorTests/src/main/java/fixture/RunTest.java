package fixture;
import frontend.StartTestEditor;


public class RunTest {
	public static void main(String[] args) {
		String testfile = "E:\\Fara\\testEditorTests\\automatisierte_tests\\EDI_editor_tests\\EDI_01_Processing\\EDI_01_01_Play_Success_only\\EDI_01_01_Play_Success_only.html";
		new StartTestEditor().start(new String[]{testfile});
	}
}
