package com.zandero.utils;

/**
 * Method parameter check helper
 * add additional check when needed
 */
public final class Assert {

	private Assert() { // hide constructor
	}

	public static void isTrue(boolean test, String message) {

		if (!test) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void isFalse(boolean test, String message) {

		isTrue(!test, message);
	}

	public static void isNull(Object test, String message) {

		isTrue(test == null, message);
	}

	public static void notNull(Object test, String message) {

		isFalse(test == null, message);
	}

	public static void notNullOrEmptyTrimmed(String value, String message) {

		isFalse(StringUtils.isNullOrEmptyTrimmed(value), message);
	}
}
