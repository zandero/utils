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
	public void getResourceLinesTest() {

		Set<String> lines = ResourceUtils.getResourceLines("/keywords/english.txt", this.getClass());
		assertNotNull(lines);
		assertEquals(183, lines.size());
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

	@Test(expected = IOException.class)
	public void readFileTest_Fail() throws IOException {

		String filename = this.getClass().getResource("/").getFile();

		try {

			File file = new File(filename);
			ResourceUtils.readFileToString(file);
		}
		catch (IOException e) {

			assertTrue(e.getMessage(), e.getMessage().endsWith("zandero/utils/target/test-classes' is a directory"));
			throw e;
		}
	}

	@Test(expected = IOException.class)
	public void readFileTest_Fail2() throws IOException {

		try {

			File file = new File("nonExistent.file");
			ResourceUtils.readFileToString(file);
		}
		catch (IOException e) {

			assertEquals("File 'nonExistent.file' does not exist", e.getMessage());
			throw e;
		}
	}
}