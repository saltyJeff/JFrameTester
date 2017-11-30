package io.github.saltyJeff.jframetester;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipTester {
	private File root;
	private File outFile;
	private String projectName;
	private boolean compile;
	public ZipTester(File zipRoot, File outDir, String projName, boolean c) throws Exception {
		compile = c;
		root = zipRoot.getAbsoluteFile();
		projectName = projName;
		outFile = new File(outDir, projectName+".png");
		outFile.createNewFile();
	}
	public void execClassByName(String n) throws Exception {
		System.out.format("INFO:\t attempting to test %s\n", projectName);
		File cantFindAClassFile = null;
		ArrayList<File> possibleFiles = getClassOrJavaFiles();
		for (File file: possibleFiles) {
			Path entry = file.toPath();
			if(getNameFromString(entry.getFileName().toString()).equals(n)) {
				if (getExtFromString(entry.getFileName().toString()).equals(".class")) {
					GraphicsTest.testClassFile(entry.toFile(), outFile);
					System.out.format("SUCCESS:\t%s has been completed\n", projectName);
					return;
				}
				else if (getExtFromString(entry.getFileName().toString()).equals(".java")) {
					cantFindAClassFile = entry.toFile();
				}
			}
		}
		if(cantFindAClassFile != null && compile) {
			System.out.format("WARNING:\t%s does not contain compiled code\n", projectName);
			GraphicsTest.testJavaFile(cantFindAClassFile, outFile);
			System.out.format("SUCCESS:\t%s has compiled and completed\n", projectName);
		}
		else {
			throw new Exception(String.format("No file named %s.class/.java was found", n));
		}
	}
	public static String getNameFromFile(File f) {
		String str = Paths.get(f.getName()).getFileName().toString();
		if(f.isDirectory()) {
			return str;
		}
		return getNameFromString(str);
	}
	public String getExtFromFile(File f) {
		String str = Paths.get(f.getName()).getFileName().toString();
		if(f.isDirectory()) {
			return "";
		}
		return getExtFromString(str);
	}
	public static String getNameFromString(String str) {
		int index = str.lastIndexOf('.');
		if(index == -1) {
			return str;
		}
		return str.substring(0, index);
	}
	public static String getExtFromString(String str) {
		int index = str.indexOf('.');
		if(index == -1) {
			return "";
		}
		return str.substring(index);
	}
	private ArrayList<File> getClassOrJavaFiles() {
		ArrayList<File> ret = new ArrayList<>();
		recursiveSeekFiles(root, ret);
		return ret;
	}
	private void recursiveSeekFiles(File folderRoot, ArrayList<File> list) {
		File[] files = folderRoot.listFiles();
		if(files == null) {
			return;
		}
		for (File file : files) {
			if (file.isFile()) {
                String ext = getExtFromFile(file);
                if(ext.equals(".class") || ext.equals(".java")) {
                	list.add(file);
                }
			}
			else if (file.isDirectory()) {
				recursiveSeekFiles(file, list);
			}
		}
	}
}