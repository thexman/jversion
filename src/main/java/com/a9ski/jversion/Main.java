package com.a9ski.jversion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.sun.org.apache.bcel.internal.classfile.ClassParser;

public class Main {
	
	public Main() { 
		super();
	}
	
	private boolean checkFileExists(String path) {
		final File f = new File(path);
		if (!f.isFile()) {
			System.out.println(String.format("File '%s' doesn't exists or is not regular file", path));
			return false;
		} else {
			return true;
		}
	}
	
	private boolean checkDirExists(String path) {
		final File f = new File(path);
		if (!f.isDirectory()) {
			System.out.println(String.format("Directory '%s' doesn't exists or is not regular file", path));
			return false;
		} else {
			return true;
		}
	}
	
	private void printFileVersion(FileVersion v) {
		final String classPath = v.getFile().getAbsolutePath();
		printClassVersion(classPath, v);
	}

	protected void printClassVersion(final String classPath, final Version v) {
		System.out.println(String.format("class: %s, version: %s", classPath, v));
	}
	
	private void printJarVersion(JarVersion v, boolean jarInfoOnly) {
		System.out.println(String.format("jar: %s, min-version: %s, max-version: %s", v.getJarFile().getAbsolutePath(), v.getMinVersion(), v.getMaxVersion()));
		if (!jarInfoOnly) {
			for(final String classPath : v.getClassVersions().keySet()) {
				printClassVersion(classPath, v.getClassVersions().get(classPath));
			}
			System.out.println();
		}
	}
	
	public void parseClass(String classPath) throws IOException {
		if (checkFileExists(classPath)) {
			final ClassVersionParser p = new ClassVersionParser();		
			final FileVersion v = p.parseVersion(new File(classPath));
			printFileVersion(v);
		}
	}
	
	public void parseClassDir(String classDir) throws IOException {
		if (checkDirExists(classDir)) {
			final ClassVersionParser p = new ClassVersionParser();
			
			final File dir = new File(classDir);
			
			final Collection<File> classes = FileUtils.listFiles(dir, new SuffixFileFilter(".class", IOCase.INSENSITIVE), TrueFileFilter.TRUE);
			
			for(final File c : classes) {
				final FileVersion v = p.parseVersion(c);
				printFileVersion(v);				
			}
		}
	}
	
	public void parseJarFile(String jarPath, boolean jarInfoOnly) throws IOException {
		if (checkFileExists(jarPath)) {
			final JarVersionParser p = new JarVersionParser();		
			final JarVersion v = p.parseVersions(new File(jarPath));
			printJarVersion(v, jarInfoOnly);
		}
	}	
		
	public void parseJarDir(String jarDir, boolean jarInfoOnly) throws IOException {
		if (checkDirExists(jarDir)) {
			final JarVersionParser p = new JarVersionParser();
			
			final File dir = new File(jarDir);
			
			final Collection<File> jars = FileUtils.listFiles(dir, new SuffixFileFilter(".jar", IOCase.INSENSITIVE), TrueFileFilter.TRUE);
			
			for(final File jar : jars) {
				final JarVersion v = p.parseVersions(jar);
				printJarVersion(v, jarInfoOnly);				
			}
		}
	}
	
	
	

	
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException, ParseException {
		final Options options = new Options();
		
		final Option classOption = OptionBuilder.withArgName("class-file").hasArgs().withDescription("parse single class file").create("class");
		final Option jarOption = OptionBuilder.withArgName("jar-file").hasArg().withDescription("parse single jar file").create("jar");
		final Option jarDirOption = OptionBuilder.withArgName("directory").hasArg().withDescription("parse directory with jar files").create("jardir");
		final Option classDirOption = OptionBuilder.withArgName("directory").hasArg().withDescription("parse directory with class files").create("classdir");		
		final Option jarInfoOnlyOption = OptionBuilder.withDescription("prints only jars min/max versions").create("jarinfo");
		
		options.addOption(classOption);
		options.addOption(jarOption);
		options.addOption(jarDirOption);
		options.addOption(classDirOption);
		options.addOption(jarInfoOnlyOption);
		
		// create the parser
	    final CommandLineParser cmdParser = new GnuParser();
	    final CommandLine cmd = cmdParser.parse(options, args);
	    
	    
	    boolean printHelp = false;
    	final Main m = new Main();
    	boolean jarInfoOnly = cmd.hasOption(jarInfoOnlyOption.getOpt());
    	if (cmd.hasOption(classOption.getOpt())) {
    		m.parseClass(cmd.getOptionValue(classOption.getOpt()));
    	} else if (cmd.hasOption(jarOption.getOpt())) {
    		m.parseJarFile(cmd.getOptionValue(jarOption.getOpt()), jarInfoOnly);
    	} else if (cmd.hasOption(classDirOption.getOpt())) {
    		m.parseClassDir(cmd.getOptionValue(classDirOption.getOpt()));
    	} else if (cmd.hasOption(jarDirOption.getOpt())) {
    		m.parseJarDir(cmd.getOptionValue(jarDirOption.getOpt()), jarInfoOnly);	    	
    	} else {
    		printHelp = true;
    	}
	    
	    
	    if (printHelp) {
	    	final HelpFormatter formatter = new HelpFormatter();
	    	formatter.printHelp("jversion", options, true );
	    }
		
	}

}
