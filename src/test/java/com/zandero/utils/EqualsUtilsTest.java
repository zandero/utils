package com.zandero.utils;

import org.junit.jupiter.api.Test;

import static com.zandero.utils.junit.AssertFinalClass.isWellDefined;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class EqualsUtilsTest {

	@Test
	void testDefinition() {
		isWellDefined(EqualsUtils.class);
	}

	@Test
	void equalIntegers() {

		Integer one = null;
		Integer two = null;

		assertTrue(EqualsUtils.equals(one, two));

		one = 1;
		assertFalse(EqualsUtils.equals(one, null));
		assertFalse(EqualsUtils.equals(null, one));

		two = 1;
		assertTrue(EqualsUtils.equals(one, two));

		one = 2;
		assertFalse(EqualsUtils.equals(one, two));
	}

	@Test
	void equalLongs() {

		Long one = null;
		Long two = null;

		assertTrue(EqualsUtils.equals(one, two));

		one = 1L;
		assertFalse(EqualsUtils.equals(one, null));
		assertFalse(EqualsUtils.equals(null, one));

		two = 1L;
		assertTrue(EqualsUtils.equals(one, two));

		one = 2L;
		assertFalse(EqualsUtils.equals(one, two));
	}
}