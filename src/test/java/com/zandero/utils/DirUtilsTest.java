package com.zandero.utils;

import org.junit.jupiter.api.Test;

import static com.zandero.utils.junit.AssertFinalClass.isWellDefined;
import static org.junit.Assert.assertEquals;

/**
 *
 */
class DirUtilsTest {

	@Test
	void testDefinition() {
		isWellDefined(DirUtils.class);
	}

	@Test
	void getFileExtension() throws Exception {

		assertEquals("zip", DirUtils.getFileExtension("c://some/file.zip"));
		assertEquals("txt", DirUtils.getFileExtension("\\some\\file.zip.txt"));
	}

}