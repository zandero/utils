package com.zandero.utils;

import org.junit.Test;

import static com.zandero.utils.junit.AssertFinalClass.isWellDefined;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class DirUtilsTest {

	@Test
	public void testDefinition() {
		isWellDefined(DirUtils.class);
	}

	@Test
	public void getFileExtension() throws Exception {

		assertEquals("zip", DirUtils.getFileExtension("c://some/file.zip"));
		assertEquals("txt", DirUtils.getFileExtension("\\some\\file.zip.txt"));
	}

}