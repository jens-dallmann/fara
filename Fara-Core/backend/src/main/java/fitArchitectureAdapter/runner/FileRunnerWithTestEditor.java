package fitArchitectureAdapter.runner;

import fit.FileRunner;

public class FileRunnerWithTestEditor extends FileRunner{
	@Override
	public void run(String[] args) {
		args(args);
        process();
	}
}
