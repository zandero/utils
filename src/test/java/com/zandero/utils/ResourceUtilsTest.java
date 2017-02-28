package com.zandero.utils;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 */
public class ResourceUtilsTest {

	@Test
	public void getResourceLinesTest() {

		Set<String> lines = ResourceUtils.getResourceLines("/keywords/english.txt", this.getClass());
		assertNotNull(lines);
		assertEquals(183, lines.size());
	}
}