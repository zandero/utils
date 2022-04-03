package com.zandero.utils;

import org.junit.jupiter.api.*;

import static com.zandero.utils.junit.AssertFinalClass.*;
import static org.junit.jupiter.api.Assertions.*;

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