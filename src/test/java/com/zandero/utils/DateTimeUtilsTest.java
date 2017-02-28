package com.zandero.utils;

import org.junit.Test;

import static net.trajano.commons.testing.UtilityClassTestUtil.assertUtilityClassWellDefined;
import static org.junit.Assert.assertEquals;

public class DateTimeUtilsTest {

	@Test
	public void testDefinition() throws ReflectiveOperationException {

		assertUtilityClassWellDefined(DateTimeUtils.class);
	}

	@Test
	public void getUtcHourTest() {

		assertEquals(0, DateTimeUtils.getUtcHour(0, 0));
		assertEquals(23, DateTimeUtils.getUtcHour(0, 1));
		assertEquals(12, DateTimeUtils.getUtcHour(0, 12));

		assertEquals(11, DateTimeUtils.getUtcHour(0, -11));
		assertEquals(1, DateTimeUtils.getUtcHour(0, -1));
	}

	@Test
	public void getTimezoneHourTest() {

		assertEquals(0, DateTimeUtils.getTimezoneHour(0, 0));
		assertEquals(1, DateTimeUtils.getTimezoneHour(0, 1));
		assertEquals(12, DateTimeUtils.getTimezoneHour(0, 12));

		assertEquals(13, DateTimeUtils.getTimezoneHour(0, -11));
		assertEquals(23, DateTimeUtils.getTimezoneHour(0, -1));
	}

	@Test
	public void getMonthStartEndTest() {

		long start = DateTimeUtils.getMonthStart(1468402252866L); // 13.7.2016
		long end = DateTimeUtils.getMonthEnd(1468402252866L);

		assertEquals(1467331200000L, start); //  1 Jul 2016 00:00:00.000 GMT
		assertEquals(1470009599999L, end);   // 31 Jul 2016 23:59:59.999 GMT
	}

	@Test
	public void getDayOfMonth() {

		assertEquals(13, DateTimeUtils.getDayInMonth(1468402252866L)); // 13.7.2016
		assertEquals(11, DateTimeUtils.getDayInMonth(1468195200000L)); // Mon 11.7.2016
		assertEquals(3, DateTimeUtils.getDayInMonth(1467590399999L)); //  Sun 3 Jul 2016
	}

	@Test
	public void getWeekStartEndTest() {

		long start = DateTimeUtils.getWeekStart(1468402252866L); // 13.7.2016
		long end = DateTimeUtils.getWeekEnd(1468402252866L);

		assertEquals(1468195200000L, start); //  Mon 11 Jul 2016 00:00:00.000 GMT
		assertEquals(1468799999999L, end);   // Sun 17 Jul 2016 23:59:59.999 GMT

		// from month end to other month start

		start = DateTimeUtils.getWeekStart(1467158399000L); // Tue 28.6.2016
		end = DateTimeUtils.getWeekEnd(1467158399000L);

		assertEquals(1466985600000L, start); //  Mon 27 Jun 2016 00:00:00.000 GMT
		assertEquals(1467590399999L, end);   // Sun 3 Jul 2016 23:59:59.999 GMT
	}
}