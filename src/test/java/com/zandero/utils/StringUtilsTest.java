package com.zandero.utils;

import org.junit.jupiter.api.Test;

import java.util.*;

import static com.zandero.utils.junit.AssertFinalClass.isWellDefined;
import static org.junit.jupiter.api.Assertions.*;


class StringUtilsTest {

	@Test
	void testDefinition() {

		isWellDefined(StringUtils.class);
	}

	@Test
	void testEquals() {

		assertTrue(StringUtils.equals(null, null));
		assertFalse(StringUtils.equals(null, ""));
		assertFalse(StringUtils.equals("", null));
		assertTrue(StringUtils.equals("", ""));

		assertTrue(StringUtils.equals("A", "A"));
		assertFalse(StringUtils.equals("A", "a"));
	}

	@Test
	void testEqualsIgnoreCase() {

		assertTrue(StringUtils.equals(null, null, true));
		assertFalse(StringUtils.equals(null, "", true));
		assertFalse(StringUtils.equals("", null, true));
		assertTrue(StringUtils.equals("", "", true));

		assertTrue(StringUtils.equals("A", "A", true));
		assertTrue(StringUtils.equals("A", "a", true));
	}

	@Test
	void testCompare() {

		assertEquals(0, StringUtils.compare(null, null));
		assertEquals(1, StringUtils.compare("", null));
		assertEquals(-1, StringUtils.compare(null, ""));
		assertEquals(0, StringUtils.compare("", ""));
		assertEquals(0, StringUtils.compare("a", "a"));
		assertEquals(1, StringUtils.compare("aa", "a"));
	}

	@Test
	void testTrim() {

		assertNull(StringUtils.trim(null));
		assertEquals("", StringUtils.trim(""));
		assertEquals("a", StringUtils.trim(" a "));
	}

	@Test
	void testTrimToNull() {

		assertNull(StringUtils.trimToNull(null));
		assertNull(StringUtils.trimToNull(""));
		assertEquals("a", StringUtils.trimToNull(" a "));
	}

	@Test
	void testRemoveDoubleSpaces() {

		assertNull(StringUtils.trimDoubleSpaces(null));
		assertEquals("", StringUtils.trimDoubleSpaces(""));
		assertEquals("", StringUtils.trimDoubleSpaces(" "));
		assertEquals("plast. vreć. 1x500 m", StringUtils.trimDoubleSpaces(" plast.   vreć.   1x500   m  "));
		assertEquals("", StringUtils.trimDoubleSpaces(" "));
		assertEquals("a", StringUtils.trimDoubleSpaces("  a   "));
		assertEquals("a a", StringUtils.trimDoubleSpaces("  a a  "));
		assertEquals("a b c", StringUtils.trimDoubleSpaces(" a    b     c  "));
	}

	@Test
	void testRemoveSpaces() {

		assertNull(StringUtils.trimInner(null));
		assertEquals("", StringUtils.trimInner(""));
		assertEquals("", StringUtils.trimInner(" "));
		assertEquals("a", StringUtils.trimInner("  a   "));
		assertEquals("aa", StringUtils.trimInner("  a a  "));
		assertEquals("abc", StringUtils.trimInner(" a    b     c  "));
	}

	@Test
	void testTrimEnd() {

		assertNull(StringUtils.trimEnd(null));
		assertEquals("", StringUtils.trimEnd(""));
		assertEquals("", StringUtils.trimEnd(" "));
		assertEquals("  a", StringUtils.trimEnd("  a   "));
		assertEquals("  a a", StringUtils.trimEnd("  a a  "));
		assertEquals(" a    b     c", StringUtils.trimEnd(" a    b     c  "));
	}

	@Test
	void testTrimStart() {

		assertNull(StringUtils.trimStart(null));
		assertEquals("", StringUtils.trimStart(""));
		assertEquals("", StringUtils.trimStart(" "));
		assertEquals("a   ", StringUtils.trimStart("  a   "));
		assertEquals("a a  ", StringUtils.trimStart("  a a  "));
		assertEquals("a    b     c  ", StringUtils.trimStart("              a    b     c  "));
	}

	@Test//(expected = IllegalArgumentException.class)
	void testJoin_Fail() {

		Set<String> set = null;
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> StringUtils.join(set, null));
		assertEquals("Missing separator!", e.getMessage());
	}

	@Test
	void testJoin() {

		Set<String> set = new HashSet<>();
		assertEquals("", StringUtils.join(set, ","));

		set.add("A");
		assertEquals("A", StringUtils.join(set, ","));

		set.add("B");
		assertEquals("A,B", StringUtils.join(set, ","));

		assertEquals("A, B", StringUtils.join(set, ", "));
	}

	@Test
	void testJoin_Fail2() {

		List<String> list = null;
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> StringUtils.join(list, null));
		assertEquals("Missing separator!", e.getMessage());
	}

	@Test
	void testJoin2() {

		List<String> list = new ArrayList<>();
		assertEquals("", StringUtils.join(list, ","));

		list.add("A");
		assertEquals("A", StringUtils.join(list, ","));

		list.add("B");
		assertEquals("A,B", StringUtils.join(list, ","));

		assertEquals("A, B", StringUtils.join(list, ", "));

		assertEquals("A, ... [1/2]", StringUtils.join(list, ", ", 1));
	}

	@Test //(expected = IllegalArgumentException.class)
	void testJoin_Fail3() {

		String[] array = null;
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> StringUtils.join(array, null));
		assertEquals("Missing separator!", e.getMessage());
	}

	@Test
	void testJoin3() {

		String[] array = new String[]{};
		assertEquals("", StringUtils.join(array, ","));

		array = new String[]{"A"};
		assertEquals("A", StringUtils.join(array, ","));

		array = new String[]{"A", "B"};
		assertEquals("A,B", StringUtils.join(array, ","));

		assertEquals("A, B", StringUtils.join(array, ", "));
	}

	@Test // (expected = IllegalArgumentException.class)
	void testJoin_Fail4() {

		HashMap<String, String> set = null;
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> StringUtils.join(set, null));
		assertEquals("Missing separator!", e.getMessage());
	}

	@Test
	void testJoin4() {

		HashMap<String, String> set = new LinkedHashMap<>();
		assertEquals("", StringUtils.join(set, ","));

		set.put("A", "1");
		assertEquals("A=1", StringUtils.join(set, ","));

		set.put("B", "2");
		assertEquals("A=1,B=2", StringUtils.join(set, ","));

		assertEquals("A=1, B=2", StringUtils.join(set, ", "));
	}

	@Test
	void getWordsFromTextTest() {

		List<String> output = StringUtils.getWords(null);
		assertEquals(0, output.size());

		output = StringUtils.getWords("");
		assertEquals(0, output.size());

		output = StringUtils.getWords("         ");
		assertEquals(0, output.size());

		output = StringUtils.getWords(" abra kadabra");
		assertEquals(2, output.size());
		assertEquals("abra", output.get(0));
		assertEquals("kadabra", output.get(1));


		output = StringUtils.getWords(" abra, ::(12313) kadabra!");
		assertEquals(2, output.size());
		assertEquals("abra", output.get(0));
		assertEquals("kadabra", output.get(1));

		output = StringUtils.getWords(" rdeče češnje rastejo na želvi");
		assertEquals(5, output.size());
		assertEquals("rdeče", output.get(0));
		assertEquals("češnje", output.get(1));
		assertEquals("rastejo", output.get(2));
		assertEquals("na", output.get(3));
		assertEquals("želvi", output.get(4));

		output = StringUtils.getWords("JEE6");
		assertEquals(0, output.size());
	}

	@Test
	void splitTest() {

		List<String> output = StringUtils.split(" rdeče češnje rastejo na želvi", " ");
		assertEquals(5, output.size());
		assertEquals("rdeče", output.get(0));
		assertEquals("češnje", output.get(1));
		assertEquals("rastejo", output.get(2));
		assertEquals("na", output.get(3));
		assertEquals("želvi", output.get(4));
	}

	@Test
	void trimAllTest() {

		assertNull(StringUtils.trimAll(null, null));
		assertNull(StringUtils.trimAll(null, ""));
		assertNull(StringUtils.trimAll(null, "X"));

		assertEquals("", StringUtils.trimAll("", null));
		assertEquals("A", StringUtils.trimAll("A", null));

		assertEquals("aa", StringUtils.trimAll("AaAaA", "A"));
		assertEquals("AaAaA", StringUtils.trimAll("A-a-Aa-A", "-"));
	}

	@Test
	void trimTextDownTest() {

		assertEquals("T", StringUtils.trimTextDown("Text", 1));

		assertEquals("Text", StringUtils.trimTextDown("Text", 4));
		assertEquals("Text", StringUtils.trimTextDown("Text", 5));

		assertEquals("Text", StringUtils.trimTextDown("Text to be trimmed down", 5));
		assertEquals("Text to be trimmed", StringUtils.trimTextDown("Text to be trimmed down", 22));
		assertEquals("Text to be trimmed down", StringUtils.trimTextDown("Text to be trimmed down", 25));
	}

	@Test
	void toStringOrNullTest() {

		assertNull(StringUtils.toStringOrNull(null));
		assertEquals("", StringUtils.toStringOrNull(""));

		String test = "test";
		assertEquals("test", StringUtils.toStringOrNull(test));
	}

	@Test
	void getListOfCharsTest() {

		List<String> list = StringUtils.asListOfChars(null);
		assertEquals(0, list.size());

		list = StringUtils.asListOfChars("a");
		assertEquals(1, list.size());
		assertEquals("a", list.get(0));

		list = StringUtils.asListOfChars("abc");
		assertEquals(3, list.size());
		assertEquals("a", list.get(0));
		assertEquals("b", list.get(1));
		assertEquals("c", list.get(2));
	}

	@Test
	void isWordTest() {

		assertFalse(StringUtils.isWord(null));
		assertFalse(StringUtils.isWord(""));
		assertFalse(StringUtils.isWord("  "));
		assertFalse(StringUtils.isWord(" . "));
		assertFalse(StringUtils.isWord(" , "));
		assertFalse(StringUtils.isWord("test,me"));
		assertFalse(StringUtils.isWord("   !pussy-cat!  "));

		assertTrue(StringUtils.isWord("test"));
		assertTrue(StringUtils.isWord("   me  "));
		assertTrue(StringUtils.isWord("   me,  "));
		assertTrue(StringUtils.isWord("   me!  "));
		assertTrue(StringUtils.isWord("   !HELLO!  "));
		assertTrue(StringUtils.isWord("   Češka  "));
	}

	@Test
	void capitalizeTest() {

		assertNull(StringUtils.capitalize(null));
		assertEquals("A", StringUtils.capitalize("a"));
		assertEquals("Ab", StringUtils.capitalize("ab"));
		assertEquals("Abc", StringUtils.capitalize("abc"));
		assertEquals("  ", StringUtils.capitalize("  "));
		assertEquals(" A ", StringUtils.capitalize(" a "));
		assertEquals(" Hello CAPITAL ", StringUtils.capitalize(" hello CAPITAL "));
		assertEquals("Hello CAPITAL", StringUtils.capitalize("hello CAPITAL"));
	}

	@Test
	void relevanceSearch() {

		assertEquals(-1, StringUtils.relevance(null, null));
		assertEquals(-1, StringUtils.relevance(null, ""));
		assertEquals(-1, StringUtils.relevance("", null));
		assertEquals(-1, StringUtils.relevance("", ""));
		assertEquals(-1, StringUtils.relevance("abra", ""));
		assertEquals(-1, StringUtils.relevance("", "test"));

		assertEquals(0, StringUtils.relevance("abra", "a"));
		assertEquals(0, StringUtils.relevance("abra", "ab"));
		assertEquals(0, StringUtils.relevance("abra", "abr"));
		assertEquals(0, StringUtils.relevance("abra", "abra"));

		assertEquals(-1, StringUtils.relevance("abra", "abrak"));


		assertEquals(0, StringUtils.relevance("abrakadabra", "abra"));
		assertEquals(2, StringUtils.relevance("abrakadabra", "arkada"));
		assertEquals(4, StringUtils.relevance("abrakadabra", "kada"));

		assertEquals(2, StringUtils.relevance("abrakadabra", "ra"));
		assertEquals(7, StringUtils.relevance("abrakadabra", "rara"));

		assertEquals(-1, StringUtils.relevance("abrakadabra", "hu"));

		assertEquals(0, StringUtils.relevance("gapipro", "ga"));
		assertEquals(1, StringUtils.relevance("gmail", "ga"));

		assertEquals(-1, StringUtils.relevance("drey.ray@gmail.com", "gos"));
	}

	@Test
	void trimDown() {
		String text = "word1 word2 word3 word4";
		for (int i = 10; i < 25; i++) {
			System.out.println(i + ": " + StringUtils.trimTextDown(text, i, "..."));
		}

		assertEquals("word1...", StringUtils.trimTextDown(text, 13, "..."));
		assertEquals("word1 word2...", StringUtils.trimTextDown(text, 14, "..."));
		assertEquals("word1 word2...", StringUtils.trimTextDown(text, 19, "..."));
		assertEquals("word1 word2 word3...", StringUtils.trimTextDown(text, 20, "..."));
		assertEquals("word1 word2 word3 word4", StringUtils.trimTextDown(text, 23, "..."));
	}

	@Test
	void removePunctuationTest() {

		assertNull(StringUtils.removePunctuation(null));
		assertEquals("", StringUtils.removePunctuation(""));
		assertEquals("test", StringUtils.removePunctuation("test."));
		assertEquals("test for the dummy", StringUtils.removePunctuation("test, for the dummy!"));
		assertEquals("|=+^$~`", StringUtils.removePunctuation(".,;'\"{}[]\\|-_=+()*&^%$#@!~`?/"));
	}

	@Test
	void enumerateTest() {

		assertEquals("first", StringUtils.enumerate(1));
		assertEquals("second", StringUtils.enumerate(2));
		assertEquals("third", StringUtils.enumerate(3));
		assertEquals("fourth", StringUtils.enumerate(4));
		assertEquals("fifth", StringUtils.enumerate(5));
		assertEquals("6th", StringUtils.enumerate(6));
		assertEquals("101st", StringUtils.enumerate(101));
		assertEquals("102nd", StringUtils.enumerate(102));
		assertEquals("103rd", StringUtils.enumerate(103));
		assertEquals("104th", StringUtils.enumerate(104));

		assertNull(StringUtils.enumerate(null));
		assertNull(StringUtils.enumerate(0));
		assertNull(StringUtils.enumerate(-1));
	}

	@Test
	void isNullOrEmptyTest() {

		assertTrue(StringUtils.isNullOrEmpty(null));
		assertTrue(StringUtils.isNullOrEmpty(""));
		assertFalse(StringUtils.isNullOrEmpty(" "));
	}

	@Test
	void isNullOrEmptyTrimmedTest() {

		assertTrue(StringUtils.isNullOrEmptyTrimmed(null));
		assertTrue(StringUtils.isNullOrEmptyTrimmed(""));
		assertTrue(StringUtils.isNullOrEmptyTrimmed(" "));
		assertFalse(StringUtils.isNullOrEmptyTrimmed("  a"));
	}

	@Test
	void isEmptyTest() {

		assertFalse(StringUtils.isEmpty(null));
		assertTrue(StringUtils.isEmpty(""));
		assertFalse(StringUtils.isEmpty(" "));
	}

	@Test
	void isEmptyTrimmedTest() {

		assertFalse(StringUtils.isEmptyTrimmed(null));
		assertTrue(StringUtils.isEmptyTrimmed(""));
		assertTrue(StringUtils.isEmptyTrimmed(" "));
		assertFalse(StringUtils.isEmptyTrimmed(" a "));
	}

	@Test
	void unQuoteTest() {

		assertEquals("test", StringUtils.unQuote("\"test\""));
		assertEquals("", StringUtils.unQuote("\"\""));
		assertEquals("test", StringUtils.unQuote("'test'"));
		assertEquals("", StringUtils.unQuote("''"));
	}

	@Test
	void padLeftTest() {

		assertNull(StringUtils.padLeft(null, 'x', 10));
		assertEquals("1234567890", StringUtils.padLeft("1234567890", 'x', 10));

		assertEquals("xxxxx12345", StringUtils.padLeft("12345", 'x', 10));
		assertEquals("x12345", StringUtils.padLeft("12345", 'x', 6));
	}

	@Test
	void padRightTest() {

		assertNull(StringUtils.padRight(null, 'x', 10));
		assertEquals("1234567890", StringUtils.padRight("1234567890", 'x', 10));

		assertEquals("12345xxxxx", StringUtils.padRight("12345", 'x', 10));
		assertEquals("12345x", StringUtils.padRight("12345", 'x', 6));
	}

}