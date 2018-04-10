package com.zandero.utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static com.zandero.utils.junit.AssertFinalClass.isWellDefined;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 */
public class ResourceUtilsTest {

	@Test
	void testDefinition() {
		isWellDefined(ResourceUtils.class);
	}

	@Test
	void getResourceWordsTest() {

		Set<String> lines = ResourceUtils.getResourceWords("/test.txt", this.getClass());
		assertNotNull(lines);
		assertEquals(8, lines.size());
	}

	@Test
	void readFileTest() throws IOException {

		String filename = this.getClass().getResource("/test.txt").getFile();

		File file = new File(filename);
		String content = ResourceUtils.readFileToString(file);

		assertEquals("This is a test\n" +
		             "file\n" +
		             "with multiple\n" +
		             "lines", content);
	}

	@Test
	void readFileTest_Fail() throws IOException {

		String filename = this.getClass().getResource("/").getFile();

		File file = new File(filename);
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ResourceUtils.readFileToString(file));
		assertTrue(e.getMessage(), e.getMessage().endsWith("zandero/utils/target/test-classes' is a directory"));
	}

	@Test
	void readFileTest_Fail2() throws IOException {

		File file = new File("nonExistent.file");
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> ResourceUtils.readFileToString(file));
		assertEquals("File 'nonExistent.file' does not exist", e.getMessage());
	}

	@Test
	void getResourceAbsolutePathTest() {

		String filename = this.getClass().getResource("/test.txt").getFile();
		assertEquals("/Users/drejc/zandero/utils/target/test-classes/test.txt", filename);
	}
}