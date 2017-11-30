package io.github.saltyJeff.jframetester;
import java.io.File;

import javax.swing.filechooser.FileSystemView;

import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
public class CmdOpts {
	@Option(names = {"-i", "--input"}, description = "Input zips directory")
	public File zipDir = new File(System.getProperty("user.dir"));
	
	@Option(names = {"-o", "--output"}, description = "Output png directory")
	public File outputDir = new File(FileSystemView.getFileSystemView().getDefaultDirectory(), "jframetests");
	
	@Option(names = {"-c", "--compile"}, description = "Compile if no .class found?")
	public boolean compileEnabled = false;
	
	@Option(names = { "-h", "--help" }, usageHelp = true, description = "Help")
    public boolean helpRequested = false;
	
	@Parameters(arity="1", paramLabel="NAME", description = "Name of class (fully qualified)")
	public String className;
}
