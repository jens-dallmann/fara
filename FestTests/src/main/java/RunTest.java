import fitArchitectureAdapter.runner.FileRunnerWithTestEditor;


public class RunTest {
	public static void main(String[] args) {
		args = new String[]{"C:\\data\\fara\\trunk\\FestTests\\FestTests\\FilechooserTest.fit","C:\\data\\fara\\trunk\\FestTests\\FestTests\\FilechooserTest_result.html"};
		FileRunnerWithTestEditor fileRunnerWithTestEditor = new FileRunnerWithTestEditor();
		fileRunnerWithTestEditor.run(args);
	}
}
