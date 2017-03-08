package com.zandero.utils;

import org.junit.Test;

import java.util.*;

import static net.trajano.commons.testing.UtilityClassTestUtil.assertUtilityClassWellDefined;
import static org.junit.Assert.assertEquals;

public class AssertTest {

	@Test
	public void testDefinition() throws ReflectiveOperationException {

		assertUtilityClassWellDefined(Assert.class);
	}

	@Test
	public void testIsTrue()  {

		Assert.isTrue(true, "Some ex");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsTrueButFalse() {

		try {
			Assert.isTrue(false, "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test
	public void testIsNull() throws Exception {
		Assert.isNull(null, "Bla");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsNullButNot() {

		try {
			Assert.isNull(true, "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test
	public void testNotNull() throws Exception {
		Assert.notNull(true, "OK");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullButNot() {

		try {
			Assert.notNull(null, "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test
	public void testNotNullOrEmptyTrimmed() throws Exception {
		Assert.notNullOrEmptyTrimmed(" not empty ", "OK");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNotNullOrEmptyTrimmedButEmpty() throws Exception {
		try {
			Assert.notNullOrEmptyTrimmed("   ", "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test
	public void testIsFalse() throws Exception {

		Assert.isFalse(false, "Bla");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsFalseButIsNot() {

		try {
			Assert.isFalse(true, "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyList() {

		try {
			ArrayList<String> list = null;
			Assert.notNullOrEmpty(list, "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyList_2() {

		try {
			ArrayList<String> list = new ArrayList<>();
			Assert.notNullOrEmpty(list, "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyArray() {

		try {
			String[] array = null;
			Assert.notNullOrEmpty(array, "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyArray_2() {

		try {
			String[] array = new String[]{};
			Assert.notNullOrEmpty(array, "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyMap() {

		try {
			Map<String, String> map = null;
			Assert.notNullOrEmpty(map, "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyMap_2() {

		try {
			Map<String, String> map = new HashMap<>();
			Assert.notNullOrEmpty(map, "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptySet() {

		try {
			Set<String> set = null;
			Assert.notNullOrEmpty(set, "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptySet_2() {

		try {
			Set<String> set = new HashSet<>();
			Assert.notNullOrEmpty(set, "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFullList() {

		try {
			ArrayList<String> list = new ArrayList<>();
			list.add("Bla");
			Assert.isNullOrEmpty(list, "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFullArray() {

		try {
			String[] array = new String[]{"a"};
			Assert.isNullOrEmpty(array, "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFullSet() {

		try {
			Set<String> set = new HashSet<>();
			set.add("Test");
			Assert.isNullOrEmpty(set, "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFullMap() {

		try {
			Map<String, String> map = new HashMap<>();
			map.put("Test", "Test");
			Assert.isNullOrEmpty(map, "Bang");
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}
}