package com.zandero.utils;

import org.junit.jupiter.api.*;

import java.util.*;

import static com.zandero.utils.junit.AssertFinalClass.*;
import static org.junit.jupiter.api.Assertions.*;

class AssertTest {

    @Test
    void testDefinition() throws ReflectiveOperationException {

        isWellDefined(Assert.class);
    }

    @Test
    void always() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.always("Fail"));
        assertEquals("Fail", e.getMessage());
    }

    @Test
    void testIsTrue() {

        Assert.isTrue(true, "Some ex");
    }

    @Test
    void testIsTrueButFalse() {

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.isTrue(false, "Bang"));
        assertEquals("Bang", e.getMessage());
    }

    @Test
    void testIsNull() {
        Assert.isNull(null, "Bla");
    }

    @Test
    void testIsNullButNot() {

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.isNull(true, "Bang"));
        assertEquals("Bang", e.getMessage());
    }

    @Test
    void testNotNull() {
        Assert.notNull(true, "OK");
    }

    @Test
    void testNullButNot() {

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.notNull(null, "Bang"));
        assertEquals("Bang", e.getMessage());
    }

    @Test
    void testNotNullOrEmptyTrimmed() {
        Assert.notNullOrEmptyTrimmed(" not empty ", "OK");
    }

    @Test
    void testNotNullOrEmptyTrimmedButEmpty() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.notNullOrEmptyTrimmed("   ", "Bang"));
        assertEquals("Bang", e.getMessage());
    }

    @Test
    void testIsFalse() throws Exception {

        Assert.isFalse(false, "Bla");
    }

    @Test
    void testIsFalseButIsNot() {

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.isFalse(true, "Bang"));
        assertEquals("Bang", e.getMessage());
    }

    @Test
    void testOkList() {

        List<String> list = null;
        Assert.isNullOrEmpty(list, "Bang");

        list = new ArrayList<>();
        Assert.isNullOrEmpty(list, "Bang");

        list.add("test");
        Assert.notNullOrEmpty(list, "Bang");
    }

    @Test
    void testOkSet() {

        Set<String> set = null;
        Assert.isNullOrEmpty(set, "Bang");

        set = new HashSet<>();
        Assert.isNullOrEmpty(set, "Bang");

        set.add("test");
        Assert.notNullOrEmpty(set, "Bang");
    }

    @Test
    void testOkMap() {

        Map<String, String> map = null;
        Assert.isNullOrEmpty(map, "Bang");

        map = new HashMap<>();
        Assert.isNullOrEmpty(map, "Bang");

        map.put("test", "test");
        Assert.notNullOrEmpty(map, "Bang");
    }

    @Test
    void testOkArray() {

        String[] array = null;
        Assert.isNullOrEmpty(array, "Bang");

        array = new String[]{};
        Assert.isNullOrEmpty(array, "Bang");

        array = new String[1];
        array[0] = "test";
        Assert.notNullOrEmpty(array, "Bang");
    }

    @Test
    void testEmptyList() {

        ArrayList<String> list = null;
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.notNullOrEmpty(list, "Bang"));
        assertEquals("Bang", e.getMessage());
    }

    @Test
    void testEmptyList_2() {

        ArrayList<String> list = new ArrayList<>();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.notNullOrEmpty(list, "Bang"));
        assertEquals("Bang", e.getMessage());
    }

    @Test
    void testEmptyArray() {

        String[] array = null;
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.notNullOrEmpty(array, "Bang"));
        assertEquals("Bang", e.getMessage());
    }

    @Test
    void testEmptyArray_2() {

        String[] array = new String[]{};
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.notNullOrEmpty(array, "Bang"));
        assertEquals("Bang", e.getMessage());
    }

    @Test
    void testEmptyMap() {

        Map<String, String> map = null;
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.notNullOrEmpty(map, "Bang"));
        assertEquals("Bang", e.getMessage());
    }

    @Test
    void testEmptyMap_2() {

        Map<String, String> map = new HashMap<>();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.notNullOrEmpty(map, "Bang"));
        assertEquals("Bang", e.getMessage());
    }

    @Test
    void testEmptySet() {

        Set<String> set = null;
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.notNullOrEmpty(set, "Bang"));
        assertEquals("Bang", e.getMessage());
    }

    @Test
    void testEmptySet_2() {

        Set<String> set = new HashSet<>();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.notNullOrEmpty(set, "Bang"));
        assertEquals("Bang", e.getMessage());
    }

    @Test
    void testFullList() {

        ArrayList<String> list = new ArrayList<>();
        list.add("Bla");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.isNullOrEmpty(list, "Bang"));
        assertEquals("Bang", e.getMessage());
    }

    @Test
    void testFullArray() {

        String[] array = new String[]{"a"};
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.isNullOrEmpty(array, "Bang"));
        assertEquals("Bang", e.getMessage());
    }

    @Test
    void testFullSet() {

        Set<String> set = new HashSet<>();
        set.add("Test");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.isNullOrEmpty(set, "Bang"));
        assertEquals("Bang", e.getMessage());
    }

    @Test
    void testFullMap() {

        Map<String, String> map = new HashMap<>();
        map.put("Test", "Test");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> Assert.isNullOrEmpty(map, "Bang"));
        assertEquals("Bang", e.getMessage());
    }
}