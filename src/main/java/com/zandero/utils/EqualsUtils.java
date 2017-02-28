package com.zandero.utils;

import java.util.HashMap;

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

	public static boolean equals(HashMap<String, String> mapOne, HashMap<String, String> mapTwo) {

		boolean firstEmpty = mapOne == null || mapOne.isEmpty();
		boolean secondEmpty = mapTwo == null || mapTwo.isEmpty();

		if (firstEmpty && secondEmpty) {
			return true;
		}

		if (firstEmpty || secondEmpty) {
			return false;
		}

		if (mapOne.size() != mapTwo.size()) {
			return false;
		}

		// compare keys
		for (String key: mapOne.keySet()) {
			if (!mapTwo.containsKey(key)) {
				return false;
			}
		}

		return true;
	}
}
