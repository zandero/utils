package com.zandero.utils;

import org.junit.Test;

import static com.zandero.utils.junit.AssertFinalClass.isWellDefined;
import static org.junit.Assert.assertTrue;

public class JUnitProbeTest {

	@Test
	public void testDefinition() throws ReflectiveOperationException {

		isWellDefined(JUnitProbe.class);
	}

	@Test
	public void isRunningTest() {

		assertTrue(JUnitProbe.isUnitTest());
	}
}