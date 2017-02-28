package com.zandero.utils;

import org.junit.Test;

import static net.trajano.commons.testing.UtilityClassTestUtil.assertUtilityClassWellDefined;
import static org.junit.Assert.assertTrue;

public class JUnitProbeTest {

	@Test
	public void testDefinition() throws ReflectiveOperationException {

		assertUtilityClassWellDefined(JUnitProbe.class);
	}

	@Test
	public void isRunningTest() {

		assertTrue(JUnitProbe.isUnitTest());
	}
}