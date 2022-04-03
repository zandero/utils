package com.zandero.utils;

/**
 * Test out if code is running in test or not
 *
 * DEPRECATED: Will be moved to utils.junit
 */
@Deprecated
public final class JUnitProbe {

    private JUnitProbe() {
        // hide constructor
    }

    private static Boolean unitTestsRunning = null;

    /**
     * Checks if code is run inside a unit test
     *
     * @return true if unit test, false otherwise
     */
    public static boolean isUnitTest() {

        if (unitTestsRunning == null) {
            unitTestsRunning = false;

            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (StackTraceElement element : stackTrace) {
                if (element.getClassName().contains(".junit.")) {
                    unitTestsRunning = true;
                    break;
                }
            }
        }

        return unitTestsRunning;
    }
}
