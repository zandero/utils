package com.zandero.utils;

import org.junit.jupiter.api.Test;

import static com.zandero.utils.junit.AssertFinalClass.isWellDefined;
import static org.junit.Assert.assertTrue;

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