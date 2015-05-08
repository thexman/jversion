package com.a9ski.jversion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class JarVersionParser {
	
	public JarVersion parseVersions(File jarFile) throws IOException {
		final InputStream is = new FileInputStream(jarFile);
		try {
			final Map<String, Version> classVersions = parseVersions(is);
			final JarVersion ver = new JarVersion(jarFile, classVersions, VersionUtils.minVersion(classVersions.values()), VersionUtils.maxVersion(classVersions.values()));
			is.close();
			return ver;
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	
	public Map<String, Version> parseVersions(InputStream is) throws IOException {
		final ClassVersionParser versionReader = new ClassVersionParser();
		return parseVersions(versionReader, is);
	}

	protected Map<String, Version> parseVersions(final ClassVersionParser versionReader, InputStream is) throws IOException {
		final Map<String, Version> classVersions = new TreeMap<String, Version>();
		final ZipInputStream zis = new ZipInputStream(is);
		try {
			ZipEntry zipEntry;
			while( (zipEntry = zis.getNextEntry()) != null ) {
				final String name = zipEntry.getName();
				if (isClassFile(name)) {
					final Version ver = versionReader.parseVersion(zis);
					if (ver != null) {
						classVersions.put(name, ver);
					}
				}
			}
			zis.close();
		} finally {
			IOUtils.closeQuietly(zis);
		}
		return classVersions;
	}

	protected boolean isClassFile(final String name) {
		return StringUtils.endsWithIgnoreCase(name, ".class");
	}
}
