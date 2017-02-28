package com.zandero.utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static net.trajano.commons.testing.UtilityClassTestUtil.assertUtilityClassWellDefined;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class KeywordUtilsTest {

	@Test
	public void testDefinition() throws ReflectiveOperationException {

		assertUtilityClassWellDefined(KeywordUtils.class);
	}

	@Test
	public void extractKeywordsTest() {

		Set<String> keywords = KeywordUtils.extractKeywords("Evaluates and calculates costs of phone calls according to source, destination, duration and associated price plan",
			KeywordUtils.getStopWords(), 100);

		assertEquals(12, keywords.size());

		assertTrue(keywords.contains("according"));
		assertTrue(keywords.contains("evaluates"));
		assertTrue(keywords.contains("calculates"));
		assertTrue(keywords.contains("costs"));
		assertTrue(keywords.contains("phone"));
		assertTrue(keywords.contains("calls"));
		assertTrue(keywords.contains("according"));
		assertTrue(keywords.contains("destination"));
		assertTrue(keywords.contains("associated"));
		assertTrue(keywords.contains("price"));
		assertTrue(keywords.contains("plan"));
		assertTrue(keywords.contains("source"));

		// 2.
		keywords = KeywordUtils.extractKeywords("Windows Explorer Extension to Operate Git; Mirror of official repository https://tortoisegit.org/sourcecode",
			KeywordUtils.getStopWords(), 6);

		assertEquals(6, keywords.size());
		assertTrue(keywords.contains("windows"));
		assertTrue(keywords.contains("explorer"));
		assertTrue(keywords.contains("extension"));
		assertTrue(keywords.contains("operate"));
		assertTrue(keywords.contains("git"));
		assertTrue(keywords.contains("mirror"));
		/*assertTrue(keywords.contains("official"));
		assertTrue(keywords.contains("repository"));*/

		keywords = KeywordUtils.extractKeywords("This contains the latest examples for my book \"Realizing Enterprise Architectural Patterns with Maven and JEE6\"",
			KeywordUtils.getStopWords(), 100);

		assertEquals(9, keywords.size());
		assertTrue(keywords.contains("contains"));
		assertTrue(keywords.contains("latest"));
		assertTrue(keywords.contains("examples"));
		assertTrue(keywords.contains("book"));
		assertTrue(keywords.contains("realizing"));
		assertTrue(keywords.contains("enterprise"));
		assertTrue(keywords.contains("architectural"));
		assertTrue(keywords.contains("patterns"));
		assertTrue(keywords.contains("maven"));
		// assertTrue(keywords.contains("JEE6"));

		keywords = KeywordUtils.extractKeywords("Roger's internal load balancer and frontend proxy. Based on https://github.com/QubitProducts/bamboo", KeywordUtils.getStopWords(), 0);

		assertEquals(7, keywords.size());
		assertTrue(keywords.contains("roger"));
		assertTrue(keywords.contains("internal"));
		assertTrue(keywords.contains("load"));
		assertTrue(keywords.contains("balancer"));
		assertTrue(keywords.contains("frontend"));
		assertTrue(keywords.contains("proxy"));
		assertTrue(keywords.contains("based"));
	}

	@Test
	public void sortByAppearanceTest() {

		Map<String, Integer> keywords = new HashMap<>();
		keywords.put("A", 10);
		keywords.put("B", 11);
		keywords.put("C", 1);
		keywords.put("D", 14);
		keywords.put("E", 3);

		Map<String, Integer> out = KeywordUtils.sortByAppearance(keywords, 3);
		assertEquals(3, out.size());

		assertEquals(14, out.get("D").intValue());
		assertEquals(11, out.get("B").intValue());
		assertEquals(10, out.get("A").intValue());
	}
}