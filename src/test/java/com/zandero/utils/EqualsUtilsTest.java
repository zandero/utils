package com.zandero.utils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class EqualsUtilsTest {

	@Test
	public void equalIntegers() {

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
	public void equalLongs() {

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