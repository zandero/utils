package com.zandero.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Test out if code is running in test or not
 */
public final class JUnitProbe {

	private JUnitProbe() {
		// hide constructor
	}

	private static Boolean unitTestsRunning = null;

	public static boolean isUnitTest() {

		if (unitTestsRunning == null) {
			unitTestsRunning = false;

			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			List<StackTraceElement> list = Arrays.asList(stackTrace);
			for (StackTraceElement element : list) {
				if (element.getClassName().contains(".junit.")) {
					unitTestsRunning = true;
					break;
				}
			}
		}

		return unitTestsRunning;
	}
}
