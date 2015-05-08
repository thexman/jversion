package com.a9ski.jversion;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class ClassVersionParser {
	
	public ClassVersionParser() {
		super();
	}
	
	public Version parseVersion(final InputStream classByteStream) throws IOException  {
	    final DataInputStream dataInputStream = new DataInputStream ( classByteStream );
	    final int magicNumber = dataInputStream.readInt();
	    final Version ver;
	    if ( magicNumber == 0xCAFEBABE ) {
	        final int minorVer = dataInputStream.readUnsignedShort();
	        final int majorVer = dataInputStream.readUnsignedShort();
	        ver = new Version(majorVer, minorVer);
	    }  else {
	    	ver = null;
	    }
	    return ver;
	}
	
	public FileVersion parseVersion(final File file) throws IOException  {
		final InputStream is = new FileInputStream(file);
		try {
			final Version ver = parseVersion(is);
			is.close();
			return new FileVersion(file, ver);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
}
