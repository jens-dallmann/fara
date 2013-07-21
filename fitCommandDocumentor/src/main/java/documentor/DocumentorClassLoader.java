package documentor;


public class DocumentorClassLoader extends ClassLoader {
	public DocumentorClassLoader(ClassLoader parent, ClassLoader junitLoader) {
		super(parent);
	}

	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {
		return super.loadClass(name, resolve);
	}
}
