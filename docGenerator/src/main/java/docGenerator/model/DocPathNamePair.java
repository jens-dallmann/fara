package docGenerator.model;

public class DocPathNamePair {
	private final String path;
	private final String htmlFileName;

	public DocPathNamePair(String path, String htmlFileName) {
		this.path = path;
		this.htmlFileName = htmlFileName;
		
	}

	public String getHtmlFileName() {
		return htmlFileName;
	}

	public String getPath() {
		return path;
	}
}
