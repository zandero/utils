package com.zandero.utils;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

import static com.zandero.utils.junit.AssertFinalClass.isWellDefined;
import static org.junit.Assert.*;

/**
 * Map mainuplation utils test
 */
public class MapUtilsTest {

	@Test
	void testDefinition() throws ReflectiveOperationException {

		isWellDefined(MapUtils.class);
	}

	@Test
	void testMergeMapEmpty() {

		Map<String, Long> m1 = new HashMap<>();

		Map<String, Long> m2 = new HashMap<>();
		m2.put("m2_1", 2L);
		m2.put("m1_2", 3L);
		m2.put("m1_3", 1L);

		Map<String, Long> result = MapUtils.merge(Stream.of(m1, m2), Long::sum, HashMap::new);
		assertEquals(3, result.size());
	}

	@Test
	void testMergeMapEmptyL1Simple() {

		Map<String, Long> m1 = new HashMap<>();
		m1.put("m1_1", 23L);

		Map<String, Long> m2 = new HashMap<>();
		m2.put("m2_1", 2L);
		m2.put("m2_2", 3L);
		m2.put("m2_3", 1L);

		Map<String, Long> result = MapUtils.merge(Stream.of(m1, m2), Long::sum, HashMap::new);
		assertEquals(4, result.size());
	}

	@Test
	void testMergeMapEmptyL1SimpleMix() {

		Map<String, Long> m1 = new HashMap<>();
		m1.put("m1_1", 23L);
		m1.put("m2_1", 23L);

		Map<String, Long> m2 = new HashMap<>();
		m2.put("m2_1", 2L);
		m2.put("m2_2", 3L);
		m2.put("m2_3", 1L);

		Map<String, Long> result = MapUtils.merge(Stream.of(m1, m2), Long::sum, HashMap::new);
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
	void splitMap() {

		Map<String, Long> map = new HashMap<>();
		map.put("m2_1", 2L);
		map.put("m1_2", 3L);
		map.put("m1_3", 1L);

		List<Map<String, Long>> list = MapUtils.split(map, 1);
		assertEquals(3, list.size());

		Set<String> keys = new HashSet<>();
		for (Map<String, Long> mapItem : list) {
			assertEquals(1, mapItem.size());

			keys.addAll(mapItem.keySet());
		}

		assertEquals(3, keys.size());
		assertTrue(keys.contains("m2_1"));
		assertTrue(keys.contains("m1_2"));
		assertTrue(keys.contains("m1_3"));
	}

	@Test
	void splitHugeMap() {

		System.out.println("Preparing data ... ");
		Map<Integer, String> map = new HashMap<>();
		for (int i = 0; i < 5_140_000; i++) {
			map.put(i, "" + i);
		}

		Instant start = Instant.now();

		System.out.println("Starting split ... ");

		// split into 100k chunks
		List<Map<Integer, String>> list = MapUtils.split(map, 500_000);

		Duration duration = Duration.between(start, Instant.now());

		System.out.println("Duration of split: " + duration.toMillis() + "ms");

		assertEquals(11, list.size());

		for (int i = 0; i < list.size(); i++) {
			if (i < list.size() - 1) {
				assertEquals(500_000, list.get(i).size());
			} else { // last chunk should have only 40_000 enties
				assertEquals(140_000, list.get(i).size());
			}
		}
	}

	@Test
	void sortMapByValueTest() {

		Random random = new Random(System.currentTimeMillis());
		Map<String, Integer> testMap = new HashMap<>(1000);
		for (int i = 0; i < 1000; ++i) {
			testMap.put("SomeString" + random.nextInt(), random.nextInt());
		}

		testMap = MapUtils.sort(testMap, Comparator.comparing(Map.Entry::getValue));
		assertEquals(1000, testMap.size());

		Integer previous = null;
		for (Map.Entry<String, Integer> entry : testMap.entrySet()) {

			assertNotNull(entry.getValue());
			if (previous != null) {
				assertTrue(entry.getValue() >= previous);
			}
			previous = entry.getValue();
		}
	}

	@Test
	void sortMapByComplexValueTest() {

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
	void compareMapTest() {
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

	@Test
	void getFirstValueTest() {

		assertNull(MapUtils.firstValue(null));

		Map<String, Long> map = new LinkedHashMap<>();
		assertNull(MapUtils.firstValue(map));

		map.put("One", 1L);
		map.put("Two", 2L);
		map.put("Three", 3L);

		assertEquals(1L, MapUtils.firstValue(map).longValue());
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
}