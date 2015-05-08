package com.a9ski.jversion;

import java.io.File;

public class FileVersion extends Version {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8111741671624758195L;
	
	private final File file;

	public FileVersion(final File file, final int major, final int minor) {
		super(major, minor);
		this.file = file;
	}
	
	public FileVersion(final File file, final Version version) {
		this(file, version.getMajor(), version.getMinor());
	}
	
	public File getFile() {
		return file;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileVersion other = (FileVersion) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		return true;
	}
}
