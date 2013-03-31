import testEditor.frontend.StartTestEditor;


public class RunTest {
	public static void main(String[] args) {
		args = new String[]{"C:\\data\\fara\\trunk\\FestTests\\FestTests\\ComponentTest.fit","C:\\data\\fara\\trunk\\FestTests\\FestTests\\ComponentTest_result.html"};
		StartTestEditor testeditor = new StartTestEditor();
		testeditor.start(new String[]{args[0]});
	}
}
