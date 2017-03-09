package com.zandero.utils;

import java.io.*;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Utility class to read resources
 */
public final class ResourceUtils {

	private ResourceUtils() {
		// hide constructor
	}

	/**
	 * Loads resource to String
	 *
	 * @param resourceFile to read
	 * @param clazz        to use for resource access
	 * @return String representing the resource or null if resource could not be read
	 */
	public static String getResourceAsString(String resourceFile, Class clazz) {

		Scanner scanner = null;

		try {

			InputStream resource = clazz.getResourceAsStream(resourceFile);
			scanner = new Scanner(resource, EncodeUtils.UTF_8);
			return scanner.useDelimiter("\\A").next();
		}
		catch (Exception e) {
			return null;
		}
		finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	/**
	 * Loads resource as a set of Strings, where each line is added to the set
	 *
	 * @param resourceFile to read
	 * @param clazz        to use for resource access
	 * @return set of strings (lines) or null if resource could not be read
	 */
	public static Set<String> getResourceLines(String resourceFile, Class clazz) {

		Scanner scanner = null;

		try {
			InputStream resource = clazz.getResourceAsStream(resourceFile);
			scanner = new Scanner(resource, EncodeUtils.UTF_8);

			Set<String> list = new LinkedHashSet<>();
			while (scanner.hasNext()) {
				String next = scanner.next();
				if (next != null && next.trim().length() > 0) {
					list.add(next);
				}
			}

			return list;
		}
		catch (Exception e) {
			return null;
		}
		finally {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	/**
	 * Get resource last modified date
	 *
	 * @param resourceFile to read
	 * @param clazz        to use for resource access
	 * @return last modified date or null if resource could not be read
	 */
	public static Long getLastModifiedTime(String resourceFile, Class clazz) {

		try {
			URL url = clazz.getResource(resourceFile);
			return url.openConnection().getLastModified(); // get last modified date of resource
		}
		catch (IOException e) {
			return null;
		}
	}

	public static String getString(final InputStream is) {

		return getString(is, 1000);
	}

	public static String getString(final InputStream is, final int bufferSize) {

		return getString(is, bufferSize, EncodeUtils.UTF_8);
	}

	public static String getString(final InputStream is, final int bufferSize, String encoding) {

		if (is == null) {
			return null;
		}
		final char[] buffer = new char[bufferSize];
		final StringBuilder out = new StringBuilder();
		try {
			try (Reader in = new InputStreamReader(is, encoding)) {
				for (; ; ) {
					int rsz = in.read(buffer, 0, buffer.length);
					if (rsz < 0) {
						break;
					}
					out.append(buffer, 0, rsz);
				}
			}
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		return out.toString();
	}

	public static byte[] getBytes(InputStream is) {

		return getBytes(is, 100000);
	}

	private static byte[] getBytes(final InputStream is, final int bufferSize) {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[bufferSize];
		try {

			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();
		}
		catch (IOException ignored) {
		}

		return buffer.toByteArray();
	}

	/**
	 * Reads file into String
	 * @param file to be read
	 * @return file content
	 * @throws IOException in case file does't exist or is not a file
	 */
	public static String readFileToString(File file) throws IOException {

		if (!file.exists()) {
			throw new FileNotFoundException("File '" + file + "' does not exist");
		}

		if (file.isDirectory()) {
			throw new IOException("File '" + file + "' is a directory");
		}
		else if (!file.canRead()) {
			throw new IOException("File '" + file + "' cannot be read");
		}

		FileInputStream stream = new FileInputStream(file);
		return getString(stream);
	}
}
