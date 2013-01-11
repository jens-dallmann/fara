/*******************************************************************************
 * Copyright (c) 2013 Jens Dallmann.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jens Dallmann - initial API and implementation
 ******************************************************************************/

package docGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import docGenerator.model.FitCommandDoc;
import docGenerator.processors.impl.ClassFileProcessor;
import docGenerator.processors.impl.FolderFileProcessor;

public class GeneratorStarter {

	public static void main(String[] args) {
		String path = "C:" + File.separator + "data" + File.separator + "fara"
				+ File.separator + "trunk" + File.separator + "festFaraAdapter"
				+ File.separator + "target" + File.separator + "classes";
		List<FitCommandDoc> process = collectCommandsForPath(path);
		Collections.sort(process);
		String buildHtml = new HTMLBuilder().build(process);
		try {
			writeToFileCreateIfNotExist("FitCommandDocumentation.html",
					buildHtml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static List<FitCommandDoc> collectCommandsForPath(String path) {
		FileClassLoader classLoader = new FileClassLoader(path);
		FolderFileProcessor processor = new FolderFileProcessor(
				new ClassFileProcessor(classLoader), path);
		List<FitCommandDoc> process = processor.process("");
		return process;
	}

	public static File createFileIfNotExist(File file) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
		}
		return file;
	}

	public static File createFileIfNotExist(String filepath) {
		File file = new File(filepath);
		return createFileIfNotExist(file);
	}

	public static boolean writeToFile(File file, String content)
			throws IOException {
		FileWriter fileWriter = new FileWriter(file, false);
		return writeToFile(fileWriter, content);
	}

	public static boolean writeToFile(FileWriter fileWriter, String content)
			throws IOException {
		try {
			fileWriter.write(content);
		} catch (IOException ioex) {
			throw ioex;
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException ioex2) {
				}
			}
		}
		return true;

	}

	public static boolean writeToFile(String filepath, String content)
			throws IOException {
		File file = new File(filepath);
		return writeToFile(file, content);
	}

	public static boolean writeToFileCreateIfNotExist(File file, String content)
			throws IOException {
		createFileIfNotExist(file);
		return writeToFile(file, content);
	}

	public static boolean writeToFileCreateIfNotExist(String filepath,
			String content) throws IOException {
		createFileIfNotExist(filepath);
		return writeToFile(filepath, content);
	}
}
