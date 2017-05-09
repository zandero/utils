package com.zandero.utils;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.*;

/**
 *
 */
public class ResourceUtilsTest {

	@Test
	public void getResourceWordsTest() {

		Set<String> lines = ResourceUtils.getResourceWords("/test.txt", this.getClass());
		assertNotNull(lines);
		assertEquals(8, lines.size());
	}

	@Test
	public void readFileTest() throws IOException {

		String filename = this.getClass().getResource("/test.txt").getFile();

		File file = new File(filename);
		String content = ResourceUtils.readFileToString(file);

		assertEquals("This is a test\n" +
			"file\n" +
			"with multiple\n" +
			"lines", content);
	}

	@Test(expected = IllegalArgumentException.class)
	public void readFileTest_Fail() throws IOException {

		String filename = this.getClass().getResource("/").getFile();

		try {

			File file = new File(filename);
			ResourceUtils.readFileToString(file);
		}
		catch (IllegalArgumentException e) {

			assertTrue(e.getMessage(), e.getMessage().endsWith("zandero/utils/target/test-classes' is a directory"));
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void readFileTest_Fail2() throws IOException {

		try {

			File file = new File("nonExistent.file");
			ResourceUtils.readFileToString(file);
		}
		catch (IllegalArgumentException e) {

			assertEquals("File 'nonExistent.file' does not exist", e.getMessage());
			throw e;
		}
	}

	@Test
	public void getResourceAbsolutePathTest() {

		String filename = this.getClass().getResource("/test.txt").getFile();
		assertEquals("/Users/drejc/zandero/utils/target/test-classes/test.txt", filename);
	}
}