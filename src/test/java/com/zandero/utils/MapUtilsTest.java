package com.zandero.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Map mainuplation utils test
 */
public class MapUtilsTest {

	@Test
	public void testMergeMapEmpty() {

		Map<String, Long> m1 = new HashMap<>();
		Map<String, Long> m2 = new HashMap<>();
		m2.put("m2_1", 2l);
		m2.put("m1_2", 3l);
		m2.put("m1_3", 1l);

		Map<String, Long> result = MapUtils.mergeMaps(Arrays.asList(m1, m2).stream(), Long::sum, HashMap::new);
		assertEquals(3, result.size());
	}

	@Test
	public void testMergeMapEmptyL1Simple() {

		Map<String, Long> m1 = new HashMap<>();
		m1.put("m1_1", 23l);
		Map<String, Long> m2 = new HashMap<>();
		m2.put("m2_1", 2l);
		m2.put("m2_2", 3l);
		m2.put("m2_3", 1l);

		Map<String, Long> result = MapUtils.mergeMaps(Arrays.asList(m1, m2).stream(), Long::sum, HashMap::new);
		assertEquals(4, result.size());
	}

	@Test
	public void testMergeMapEmptyL1SimpleMix() {

		Map<String, Long> m1 = new HashMap<>();
		m1.put("m1_1", 23l);
		m1.put("m2_1", 23l);
		Map<String, Long> m2 = new HashMap<>();
		m2.put("m2_1", 2l);
		m2.put("m2_2", 3l);
		m2.put("m2_3", 1l);

		Map<String, Long> result = MapUtils.mergeMaps(Arrays.asList(m1, m2).stream(), Long::sum, HashMap::new);
		assertEquals(4, result.size());
		assertEquals(25l, result.get("m2_1").longValue());

	}

	@Test
	public void testMergeMapLevel2() {

		Map<String, Long> m1 = new HashMap<>();
		m1.put("m1_1", 2l);
		m1.put("m1_2", 3l);
		Map<String, Long> m2 = new HashMap<>();
		m2.put("m2_1", 2l);
		m2.put("m1_2", 3l);
		m2.put("m1_3", 1l);

		Map<String, Map<String, Long>> master1 = new HashMap<>();
		master1.put("m1", m1);
		master1.put("m2", m1);
		Map<String, Map<String, Long>> master2 = new HashMap<>();
		master2.put("m2", m2);

//		master1.forEach((k,v) -> master2.merge(k, v, (mm1, mm2) -> { Map<String, Long> mx = new HashMap<>(mm1); mm2.forEach((kk, vv) -> mx.merge(kk, vv, Long::sum)); return mx;}));
//		m1.forEach((k, v) -> m2.merge(k, v, Long::sum));

		MapUtils.mergeMapsLevel2Sum(master1, master2);

		assertEquals(2, master1.size());
		assertEquals(2, master1.get("m1").size()); // m1
		assertEquals(4, master1.get("m2").size()); // m2 merged m1 & m2
		assertEquals(2, master1.get("m2").get("m1_1").longValue());
		assertEquals(6, master1.get("m2").get("m1_2").longValue());
		assertEquals(1, master1.get("m2").get("m1_3").longValue());
		assertEquals(2, master1.get("m2").get("m2_1").longValue());

	}

	@Test
	public void testMergeMapLevel2Complex() {

		Map<String, Long> m1 = new HashMap<>();
		m1.put("m1_1", 2l);
		m1.put("m1_2", 3l);
		Map<String, Long> m2 = new HashMap<>();
		m2.put("m2_1", 2l);
		m2.put("m1_2", 3l);
		m2.put("m1_3", 1l);

		Map<String, Map<String, Long>> master1 = new HashMap<>();
		master1.put("m1", m1);
		master1.put("m2", m1);
		Map<String, Map<String, Long>> master2 = new HashMap<>();
		master2.put("m2", m2);

//		master1.forEach((k,v) -> master2.merge(k, v, (mm1, mm2) -> { Map<String, Long> mx = new HashMap<>(mm1); mm2.forEach((kk, vv) -> mx.merge(kk, vv, Long::sum)); return mx;}));
//		m1.forEach((k, v) -> m2.merge(k, v, Long::sum));

		Map<String, Map<String, Long>> result1 = MapUtils.mergeMapsLevel2Sum(master1, master2);

		assertEquals(2, result1.size());
		assertEquals(2, result1.get("m1").size()); // m1
		assertEquals(4, result1.get("m2").size()); // m2 merged m1 & m2
		assertEquals(2, result1.get("m2").get("m1_1").longValue());
		assertEquals(6, result1.get("m2").get("m1_2").longValue());
		assertEquals(1, result1.get("m2").get("m1_3").longValue());
		assertEquals(2, result1.get("m2").get("m2_1").longValue());

		// getting ready for #3
		Map<String, Map<String, Long>> master3 = new HashMap<>();
		Map<String, Long> m3 = new HashMap<>();
		m3.put("m1_1", 2l);
		m3.put("m2_2", 3l);
		m3.put("m4_3", 1l);
		master3.put("m3", m3);
		master3.put("m1", m3);

		Map<String, Map<String, Long>> result2 = MapUtils.mergeMapsLevel2Sum(result1, master3);

		assertEquals(3, result2.size());
		assertEquals(4, result2.get("m1").size()); // m1 merged with m3
		assertEquals(4, result2.get("m2").size()); // m2 merged with m1
		assertEquals(3, result2.get("m3").size()); // m3 single
		assertEquals(4, result2.get("m1").get("m1_1").longValue()); // 2 + 2
		assertEquals(3, result2.get("m1").get("m1_2").longValue()); // 3
		assertEquals(3, result2.get("m1").get("m2_2").longValue()); //
		assertEquals(1, result2.get("m1").get("m4_3").longValue());
		assertEquals(2, result2.get("m2").get("m1_1").longValue());
		assertEquals(6, result2.get("m2").get("m1_2").longValue());
		assertEquals(1, result2.get("m2").get("m1_3").longValue());
		assertEquals(2, result2.get("m2").get("m2_1").longValue());
		assertEquals(2, result2.get("m3").get("m1_1").longValue());
		assertEquals(3, result2.get("m3").get("m2_2").longValue());
		assertEquals(1, result2.get("m3").get("m4_3").longValue());
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
		Map<String, TestValue> result = MapUtils.sort(map, new Comparator<Map.Entry<String, TestValue>>() {
			@Override
			public int compare(Map.Entry<String, TestValue> o1, Map.Entry<String, TestValue> o2) {

				return o1.getValue().size - o2.getValue().size;
			}
		});

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
		result = MapUtils.sort(map, new Comparator<Map.Entry<String, TestValue>>() {
			@Override
			public int compare(Map.Entry<String, TestValue> o1, Map.Entry<String, TestValue> o2) {

				return o1.getValue().name.compareTo(o2.getValue().name);
			}
		});

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

	private class TestValue implements Comparable {

		public TestValue(String valueName, int valueSize) {

			name = valueName;
			size = valueSize;
		}

		public String name;

		public int size;

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