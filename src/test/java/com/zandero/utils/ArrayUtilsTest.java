package com.zandero.utils;

import org.junit.jupiter.api.Test;

import static com.zandero.utils.junit.AssertFinalClass.isWellDefined;
import static org.junit.Assert.assertEquals;

/**
 *
 */
class ArrayUtilsTest {

	@Test
	void checkFinalClass() {
		isWellDefined(ArrayUtils.class);
	}

	@Test
	void joinTest() {

		Integer[] one = new Integer[]{1, 2, 3};
		Integer[] two = new Integer[]{4, 5, 6};

		Integer[] out = ArrayUtils.join(one, two);

		// check order
		for (int i = 1; i <= 6; i++) {
			assertEquals(i, out[i - 1].intValue());
		}
	}

	@Test
	void joinWithNullTest() {

		Integer[] one = null;
		Integer[] two = new Integer[]{1, 2, 3};

		Integer[] out = ArrayUtils.join(one, two);

		// check order
		for (int i = 1; i <= 3; i++) {
			assertEquals(i, out[i - 1].intValue());
		}
	}
}
