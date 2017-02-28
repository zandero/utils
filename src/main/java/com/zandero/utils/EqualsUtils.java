package com.zandero.utils;

/**
 *
 */
public final class EqualsUtils {

	private EqualsUtils() {
		// hide
	}

	public static boolean equals(Long one, Long two) {

		if (one == null && two == null) {
			return true;
		}

		return one != null && two != null && one.equals(two);
	}

	public static boolean equals(Integer one, Integer two) {

		if (one == null && two == null) {
			return true;
		}

		return one != null && two != null && one.equals(two);
	}
}
