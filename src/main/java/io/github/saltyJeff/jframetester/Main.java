package io.github.saltyJeff.jframetester;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.lingala.zip4j.core.ZipFile;
import picocli.CommandLine;

public class Main {
	public static CmdOpts opts;
	public static Map<String, Exception> failedTests = new HashMap<String, Exception>();
	public static void main(String[] args) throws Exception {
		opts = CommandLine.populateCommand(new CmdOpts(), args);
		if(opts.helpRequested || opts.className == null || opts.className.equals("")) {
			CommandLine.usage(opts, System.out);
			return;
		}
		System.out.println("Running JFrame Tests...");
		System.out.println("ZIP DIRECTORY: "+opts.zipDir.getAbsolutePath());
		System.out.println("PNG DIRECTORY: "+opts.outputDir.getAbsolutePath());
		System.out.println("CLASS NAME: "+opts.className);
		System.out.println("COMPILER: "+(opts.compileEnabled ? "ENABLED" : "DISABLED"));
		opts.outputDir.mkdirs();
		
		System.out.println("------------------------------------");
		System.out.println();
		
		runZipsInDirectory(opts.zipDir);
		
		System.out.println("TESTS COMPLETE.");
		if(failedTests.isEmpty()) {
			return;
		}
		System.out.println("Failed tests are printed below:");
		for(Map.Entry<String, Exception> err : failedTests.entrySet()) {
			System.out.println(err.getKey()+":\t"+err.getValue().toString());
		}
		System.out.println("Goodbye!");
	}
	private static void runZipsInDirectory(File dir) throws Exception {
		File[] files = dir.listFiles();
		if(files == null) {
			throw new Error ("No zips found in directory "+dir.getAbsolutePath());
		}
		File compileTempDir = new File(opts.outputDir, "compileTemp");
		for(File zip : files) {
			if(!zip.getName().endsWith((".zip"))) {
				continue;
			}
			String projName = ZipTester.getNameFromFile(zip);
			try {
				compileTempDir.mkdirs();
				ZipFile zipFile = new ZipFile(zip);
				zipFile.extractAll(compileTempDir.getAbsolutePath());
				ZipTester zt = new ZipTester(compileTempDir, opts.outputDir, projName, opts.compileEnabled);
				zt.execClassByName(opts.className);
				deleteFolder(compileTempDir);
			}
			catch (Exception e) {
				failedTests.put(projName, e);
			}
		}
	}
	private static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if(files!=null) { //some JVMs return null for empty dirs
			for(File f: files) {
				if(f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
	}
}
