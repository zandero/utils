package com.zandero.utils;

import java.util.Map;

/**
 * NPE safe comparison of same type objects
 *
 * @author drejc (Andrej Završnik)
 */
public final class EqualsUtils {

	private EqualsUtils() {
		// hide
	}

	/**
	 * Compares two Long values
	 *
	 * @param one first long
	 * @param two second long
	 * @return true if equal or both null, false otherwise
	 */
	public static boolean equals(Long one, Long two) {

		if (one == null && two == null) {
			return true;
		}

		return one != null && two != null && one.equals(two);
	}

	/**
	 * Compares two Integer values
	 *
	 * @param one first integer
	 * @param two second integer
	 * @return true if equal or both null, false otherwise
	 */
	public static boolean equals(Integer one, Integer two) {

		if (one == null && two == null) {
			return true;
		}

		return one != null && two != null && one.equals(two);
	}

	/**
	 * Substitute for StringUtils.equals()
	 * <p>
	 * Compares two strings
	 *
	 * @param one first string
	 * @param two second string
	 * @return true if equal or both null, false otherwise
	 */
	public static boolean equals(String one, String two) {

		return StringUtils.equals(one, two, false);
	}

	/**
	 * Compares two maps if they are holding same keys
	 * Stored values are not compared
	 *
	 * @param one first map
	 * @param two second map
	 * @param <T> type of object stored in map
	 * @return true if holding same keys, both empty or null, false otherwise
	 */
	public static <T> boolean equals(Map<String, T> one, Map<String, T> two) {

		return MapUtils.equals(one, two);
	}
}
