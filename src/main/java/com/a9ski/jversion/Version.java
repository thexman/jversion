package com.a9ski.jversion;

import java.io.Serializable;

public class Version implements Serializable, Comparable<Version> {		
	/**
	 * 
	 */
	private static final long serialVersionUID = -3411188519020223237L;
	
	private final int major;
	private final int minor;
	
	public Version(int major, int minor) {
		super();
		this.major = major;
		this.minor = minor;
	}
	
	public int getMajor() {
		return major;
	}
	
	public int getMinor() {
		return minor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + major;
		result = prime * result + minor;
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
		Version other = (Version) obj;
		if (major != other.major)
			return false;
		if (minor != other.minor)
			return false;
		return true;
	}
	
	@Override
	public int compareTo(Version o) {
		if (getMajor() < o.getMajor()) {
			return -1;
		} else if (getMajor() > o.getMajor()) {
			return 1;
		} else if (getMinor() < o.getMinor()) {
			return -1;
		} else if (getMinor() > o.getMinor()) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return String.format("%d.%d", getMajor(), getMinor());
	}
	
}
