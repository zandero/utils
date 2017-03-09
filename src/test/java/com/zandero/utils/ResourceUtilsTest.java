package com.zandero.utils;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
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

	@Test
	public void readFileTest() throws IOException {

		String filename = this.getClass().getResource("/test.txt").getFile();

		File file =  new File(filename);
		String content = ResourceUtils.readFile(file);

		assertEquals("This is a test\n" +
			"file\n" +
			"with multiple\n" +
			"lines", content);
	}
}