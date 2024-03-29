package com.zandero.utils;

import org.junit.jupiter.api.*;

import java.time.*;
import java.util.*;

import static com.zandero.utils.junit.AssertFinalClass.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class SetUtilsTest {

    @Test
    void isWellDefinedTest() {

        isWellDefined(SetUtils.class);
    }

    @Test
    void splitSet() {

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
    void splitHugeSet() {

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

    @Test
    void arrayToSet() {

        Set<String> stringSet = SetUtils.from("a", "b", "c");
        assertEquals(3, stringSet.size());
        assertTrue(stringSet.contains("a"));
        assertTrue(stringSet.contains("b"));
        assertTrue(stringSet.contains("c"));

        Set<Integer> integerSet = SetUtils.from(1, 1, 2);
        assertEquals(2, integerSet.size());
        assertTrue(integerSet.contains(1));
        assertTrue(integerSet.contains(2));
    }
}