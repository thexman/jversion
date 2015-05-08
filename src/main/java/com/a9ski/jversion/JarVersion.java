package com.a9ski.jversion;

import java.io.File;
import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class JarVersion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8256784384755638998L;

	private final File jarFile;
	
	private final Map<String, Version> classVersions;
	
	private final Version minVersion;
	private final Version maxVersion;

	public JarVersion(final File jarFile, final Map<String, Version> classVersions, final Version minVersion, final Version maxVersion) {
		super();
		this.jarFile = jarFile;
		this.classVersions = Collections.unmodifiableMap(new TreeMap<String, Version>(classVersions));
		this.minVersion = minVersion;
		this.maxVersion = maxVersion;
	}
	
	public File getJarFile() {
		return jarFile;
	}
	
	public Map<String, Version> getClassVersions() {
		return classVersions;
	}
	
	public Version getMaxVersion() {
		return maxVersion;
	}
	
	public Version getMinVersion() {
		return minVersion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classVersions == null) ? 0 : classVersions.hashCode());
		result = prime * result + ((jarFile == null) ? 0 : jarFile.hashCode());
		result = prime * result + ((maxVersion == null) ? 0 : maxVersion.hashCode());
		result = prime * result + ((minVersion == null) ? 0 : minVersion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JarVersion other = (JarVersion) obj;
		if (classVersions == null) {
			if (other.classVersions != null)
				return false;
		} else if (!classVersions.equals(other.classVersions))
			return false;
		if (jarFile == null) {
			if (other.jarFile != null)
				return false;
		} else if (!jarFile.equals(other.jarFile))
			return false;
		if (maxVersion == null) {
			if (other.maxVersion != null)
				return false;
		} else if (!maxVersion.equals(other.maxVersion))
			return false;
		if (minVersion == null) {
			if (other.minVersion != null)
				return false;
		} else if (!minVersion.equals(other.minVersion))
			return false;
		return true;
	}

	
}
