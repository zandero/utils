package com.zandero.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

import static net.trajano.commons.testing.UtilityClassTestUtil.assertUtilityClassWellDefined;
import static org.junit.Assert.*;

/**
 * Map mainuplation utils test
 */
public class MapUtilsTest {

	@Test
	public void testDefinition() throws ReflectiveOperationException {

		assertUtilityClassWellDefined(MapUtils.class);
	}

	@Test
	public void testMergeMapEmpty() {

		Map<String, Long> m1 = new HashMap<>();

		Map<String, Long> m2 = new HashMap<>();
		m2.put("m2_1", 2L);
		m2.put("m1_2", 3L);
		m2.put("m1_3", 1L);

		Map<String, Long> result = MapUtils.mergeMaps(Stream.of(m1, m2), Long::sum, HashMap::new);
		assertEquals(3, result.size());
	}

	@Test
	public void testMergeMapEmptyL1Simple() {

		Map<String, Long> m1 = new HashMap<>();
		m1.put("m1_1", 23L);

		Map<String, Long> m2 = new HashMap<>();
		m2.put("m2_1", 2L);
		m2.put("m2_2", 3L);
		m2.put("m2_3", 1L);

		Map<String, Long> result = MapUtils.mergeMaps(Stream.of(m1, m2), Long::sum, HashMap::new);
		assertEquals(4, result.size());
	}

	@Test
	public void testMergeMapEmptyL1SimpleMix() {

		Map<String, Long> m1 = new HashMap<>();
		m1.put("m1_1", 23L);
		m1.put("m2_1", 23L);

		Map<String, Long> m2 = new HashMap<>();
		m2.put("m2_1", 2L);
		m2.put("m2_2", 3L);
		m2.put("m2_3", 1L);

		Map<String, Long> result = MapUtils.mergeMaps(Stream.of(m1, m2), Long::sum, HashMap::new);
		assertEquals(4, result.size());
		assertTrue(result.containsKey("m1_1"));
		assertTrue(result.containsKey("m2_1"));
		assertTrue(result.containsKey("m2_2"));
		assertTrue(result.containsKey("m2_3"));

		assertEquals(23L, result.get("m1_1").longValue());
		assertEquals(25L, result.get("m2_1").longValue());
		assertEquals(3L, result.get("m2_2").longValue());
		assertEquals(1L, result.get("m2_3").longValue());
	}

	@Test
	public void sortMapByValueTest() {

		Random random = new Random(System.currentTimeMillis());
		Map<String, Integer> testMap = new HashMap<>(1000);
		for (int i = 0; i < 1000; ++i) {
			testMap.put("SomeString" + random.nextInt(), random.nextInt());
		}

		testMap = MapUtils.sort(testMap, Comparator.comparing(Map.Entry::getValue));
		assertEquals(1000, testMap.size());

		Integer previous = null;
		for (Map.Entry<String, Integer> entry : testMap.entrySet()) {
			Assert.assertNotNull(entry.getValue());
			if (previous != null) {
				Assert.assertTrue(entry.getValue() >= previous);
			}
			previous = entry.getValue();
		}
	}

	@Test
	public void sortMapByComplexValueTest() {

		Map<String, TestValue> map = new HashMap<>();
		map.put("One", new TestValue("First", 10));
		map.put("Two", new TestValue("Second", 40));
		map.put("Three", new TestValue("Third", 30));
		map.put("Four", new TestValue("Fourth", 20));

		// sort by size
		Map<String, TestValue> result = MapUtils.sort(map, Comparator.comparingInt(o1 -> o1.getValue().size));

		int count = 0;
		for (String key : result.keySet()) {
			switch (count) {
				case 0:
					assertEquals("One", key);
					break;

				case 1:
					assertEquals("Four", key);
					break;

				case 2:
					assertEquals("Three", key);
					break;

				case 3:
					assertEquals("Two", key);
					break;
			}

			count++;
		}

		// sort by name
		result = MapUtils.sort(map, Comparator.comparing(o -> o.getValue().name));

		count = 0;
		for (String key : result.keySet()) {
			switch (count) {
				case 0:
					assertEquals("One", key);
					break;

				case 1:
					assertEquals("Four", key);
					break;

				case 2:
					assertEquals("Two", key);
					break;

				case 3:
					assertEquals("Three", key);
					break;
			}

			count++;
		}
	}

	@Test
	public void compareMapTest() {
		Map<String, String> one = null;
		Map<String, String> two = null;
		assertTrue(MapUtils.equals(one, two));

		one = new HashMap<>();
		assertTrue(MapUtils.equals(one, two));

		one.put("One", "A");
		one.put("Two", "B");
		one.put("Three", "C");

		two = new HashMap<>();
		assertFalse(MapUtils.equals(one, two));

		two.put("One", "X");
		two.put("Two", "B");
		two.put("Three", "C");
		assertFalse(MapUtils.equals(one, two, true));
		assertTrue(MapUtils.equals(one, two));

		two.put("One", "A");
		assertTrue(MapUtils.equals(one, two, true));

		two.put("Two", null);
		one.put("Two", null);
		assertTrue(MapUtils.equals(one, two, true));

		one.put("Two", " ");
		assertFalse(MapUtils.equals(one, two, true));

		one.remove("Two");
		assertFalse(MapUtils.equals(one, two, false));

		one.put("Four", "A");
		assertFalse(MapUtils.equals(one, two, false));
	}

	private class TestValue implements Comparable {

		TestValue(String valueName, int valueSize) {

			name = valueName;
			size = valueSize;
		}

		String name;

		int size;

		@Override
		public int compareTo(Object o) {

			if (!(o instanceof TestValue)) {
				return size;
			}

			return size - ((TestValue) o).size;
		}
	}

	@Test
	public void getFirstValueTest() {

		assertNull(MapUtils.getFirstValue(null));

		Map<String, Long> map = new LinkedHashMap<>();
		assertNull(MapUtils.getFirstValue(map));

		map.put("One", 1L);
		map.put("Two", 2L);
		map.put("Three", 3L);

		assertEquals(1L, MapUtils.getFirstValue(map).longValue());
	}
}