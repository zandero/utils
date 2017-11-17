package com.zandero.utils;

import org.junit.Test;

import java.util.*;

import static com.zandero.utils.junit.AssertFinalClass.isWellDefined;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AssertTest {

	@Test
	public void testDefinition() throws ReflectiveOperationException {

		isWellDefined(Assert.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void always() {

		try {
			Assert.always("Fail");
			fail();
		}
		catch (IllegalArgumentException e) {
			assertEquals("Fail", e.getMessage());
			throw e;
		}
	}

	@Test
	public void testIsTrue() {

		Assert.isTrue(true, "Some ex");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIsTrueButFalse() {

		try {
			Assert.isTrue(false, "Bang");
			fail();
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
			fail();
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
			fail();
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
			fail();
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
			fail();
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}

	@Test
	public void testOkList() {

		List<String> list = null;
		Assert.isNullOrEmpty(list, "Bang");

		list = new ArrayList<>();
		Assert.isNullOrEmpty(list, "Bang");

		list.add("test");
		Assert.notNullOrEmpty(list, "Bang");
	}

	@Test
	public void testOkSet() {

		Set<String> set = null;
		Assert.isNullOrEmpty(set, "Bang");

		set = new HashSet<>();
		Assert.isNullOrEmpty(set, "Bang");

		set.add("test");
		Assert.notNullOrEmpty(set, "Bang");
	}

	@Test
	public void testOkMap() {

		Map<String, String> map = null;
		Assert.isNullOrEmpty(map, "Bang");

		map = new HashMap<>();
		Assert.isNullOrEmpty(map, "Bang");

		map.put("test", "test");
		Assert.notNullOrEmpty(map, "Bang");
	}

	@Test
	public void testOkArray() {

		String[] array = null;
		Assert.isNullOrEmpty(array, "Bang");

		array = new String[]{};
		Assert.isNullOrEmpty(array, "Bang");

		array = new String[1];
		array[0] = "test";
		Assert.notNullOrEmpty(array, "Bang");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyList() {

		try {
			ArrayList<String> list = null;
			Assert.notNullOrEmpty(list, "Bang");
			fail();
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
			fail();
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
			fail();
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
			fail();
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
			fail();
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
			fail();
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
			fail();
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
			fail();
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
			fail();
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
			fail();
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
			fail();
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
			fail();
		}
		catch (IllegalArgumentException e) {
			assertEquals("Bang", e.getMessage());
			throw e;
		}
	}
}