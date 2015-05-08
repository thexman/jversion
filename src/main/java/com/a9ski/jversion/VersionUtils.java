package com.a9ski.jversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VersionUtils {
	protected VersionUtils() {
		super();
	}
	
	public static Version minVersion(Collection<Version> versions) {
		Version min = null;
		if (versions != null) {
			for(final Version v : versions) {
				if (v != null && (min == null || min.compareTo(v) < 0)) {
					min = v;
				}
			}
		}
		return min;
	}
	
	public static Version maxVersion(Collection<Version> versions) {
		Version max = null;
		if (versions != null) {
			for(final Version v : versions) {
				if (v != null && (max == null || max.compareTo(v) > 0)) {
					max = v;
				}
			}
		}
		return max;
	}
	
	public static List<String> selectWithVersion(Map<String, Version> classVersions, Version v) {
		final List<String> classes = new ArrayList<String>();
		if (classVersions != null) {
			for(final Map.Entry<String, Version> e : classVersions.entrySet()) {
				if (Objects.equals(e.getValue(), v)) {
					classes.add(e.getKey());
				}
			}
		}
		return classes;
	}
}
