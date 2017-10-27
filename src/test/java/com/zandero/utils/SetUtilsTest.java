package com.zandero.utils;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.zandero.utils.junit.AssertFinalClass.isWellDefined;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class SetUtilsTest {

	@Test
	public void isWellDefinedTest() {

		isWellDefined(SetUtils.class);
	}

	@Test
	public void splitSet() throws Exception {

		Set<String> storage = new HashSet<>();
		storage.add("m2_1");
		storage.add("m1_2");
		storage.add("m1_3");

		List<Set<String>> list = SetUtils.split(storage, 1);
		assertEquals(3, list.size());

		List<String> keys = new ArrayList<>();
		for (Set<String> mapItem : list) {
			assertEquals(1, mapItem.size());

			keys.addAll(mapItem);
		}

		assertEquals(3, keys.size());
		assertTrue(keys.contains("m2_1"));
		assertTrue(keys.contains("m1_2"));
		assertTrue(keys.contains("m1_3"));
	}

	@Test
	public void splitHugeSet() {

		System.out.println("Preparing data ... ");

		Set<String> storage = new HashSet<>();
		for (int i = 0; i < 5_140_000; i++) {
			storage.add("" + i);
		}

		Instant start = Instant.now();

		System.out.println("Starting split ... ");

		// split into 100k chunks
		List<Set<String>> list = SetUtils.split(storage, 500_000);

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
}