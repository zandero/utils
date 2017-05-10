package com.zandero.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class DirUtilsTest {

	@Test
	public void getFileExtension() throws Exception {

		assertEquals("zip", DirUtils.getFileExtension("c://some/file.zip"));
		assertEquals("txt", DirUtils.getFileExtension("\\some\\file.zip.txt"));
	}

}