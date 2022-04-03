package com.zandero.utils;

import org.junit.jupiter.api.*;

import static com.zandero.utils.junit.AssertFinalClass.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Maybe move this utility to utils.junit ?
 */
@Deprecated
class JUnitProbeTest {

    @Test
    void testDefinition() {
        isWellDefined(JUnitProbe.class);
    }

    @Test
    void isRunningTest() {
        assertTrue(JUnitProbe.isUnitTest());
    }
}