package com.zandero.utils;

import java.io.IOException;
import java.io.InputStream;
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
	 * @param resourceFile to read
	 * @param clazz to use for resource access
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
	 * @param resourceFile to read
	 * @param clazz to use for resource access
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
	 * @param resourceFile to read
	 * @param clazz to use for resource access
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
}
