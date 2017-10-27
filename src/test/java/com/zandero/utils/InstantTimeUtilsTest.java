package com.zandero.utils;

import org.junit.Test;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static com.zandero.utils.junit.AssertFinalClass.isWellDefined;
import static org.junit.Assert.*;

/**
 *
 */
public class InstantTimeUtilsTest {

	@Test
	public void testWellDefined() {

		isWellDefined(InstantTimeUtils.class);
	}

	/*@Test
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
	}*/

	@Test
	public void getMonthStartEndTest() {

		Instant time = Instant.ofEpochMilli(1468402252866L);

		Instant start = InstantTimeUtils.getMonthStart(time); // 13.7.2016
		Instant end = InstantTimeUtils.getMonthEnd(time);

		assertEquals(Instant.ofEpochMilli(1467331200000L), start); //  1 Jul 2016 00:00:00.000 GMT
		assertEquals(Instant.ofEpochMilli(1470009599999L), end);   // 31 Jul 2016 23:59:59.999 GMT
	}

	/*@Test
	public void getDayOfMonth() {

		assertEquals(13, DateTimeUtils.getDayInMonth(1468402252866L)); // 13.7.2016
		assertEquals(11, DateTimeUtils.getDayInMonth(1468195200000L)); // Mon 11.7.2016
		assertEquals(3, DateTimeUtils.getDayInMonth(1467590399999L)); //  Sun 3 Jul 2016
	}*/

	/*@Test
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
	}*/

	@Test
	public void formatTest() {

		assertEquals("2016-07-13", InstantTimeUtils.formatDate(1468402252866L));
		assertEquals("2016-07-13 09:30", InstantTimeUtils.formatDateTimeShort(1468402252866L));
		assertEquals("2016-07-13 09:30:52 +0000", InstantTimeUtils.formatDateTime(1468402252866L));

		assertEquals("2016-06-27 00:00:00 +0000", InstantTimeUtils.format(1466985600000L, null));
		assertEquals("2016-06-27 00:00:00Z", InstantTimeUtils.format(1466985600000L,
		                                                             DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX").withZone(
			                                                             ZoneOffset.UTC)));
	}


	private static final DateTimeFormatter[] FORMATS = new DateTimeFormatter[]{
		DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),
		DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.systemDefault()),
		DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"),
		DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssXXX")
	};

	@Test
	public void getTimestampFromStringTest() {

		assertEquals(null, InstantTimeUtils.getTimestamp(null, FORMATS));
		assertEquals(null, InstantTimeUtils.getTimestamp("", FORMATS));
		assertEquals(null, InstantTimeUtils.getTimestamp(" ", FORMATS));

		assertEquals(Instant.ofEpochMilli(1456133708000L), InstantTimeUtils.getTimestamp("2016-02-22T10:35:08.000+01:00", FORMATS));
		assertEquals(Instant.ofEpochMilli(1441268437309L), InstantTimeUtils.getTimestamp("2015-09-03T08:20:37.309Z", FORMATS));
		assertEquals(Instant.ofEpochMilli(1442518055000L), InstantTimeUtils.getTimestamp("2015-09-17T21:27:35Z", FORMATS));

		assertEquals(Instant.ofEpochMilli(1450770034000L), InstantTimeUtils.getTimestamp("2015-12-22 07:40:34+00:00", FORMATS));
		assertEquals(Instant.ofEpochMilli(1450766434000L), InstantTimeUtils.getTimestamp("2015-12-22 07:40:34+01:00", FORMATS));
		assertEquals(Instant.ofEpochMilli(1450773634000L), InstantTimeUtils.getTimestamp("2015-12-22 07:40:34-01:00", FORMATS));

		assertEquals(Instant.ofEpochMilli(1450770034000L), InstantTimeUtils.getTimestamp("2015-12-22T07:40:34+00:00", FORMATS));
		assertEquals(Instant.ofEpochMilli(1450766434000L), InstantTimeUtils.getTimestamp("2015-12-22T07:40:34+01:00", FORMATS));
		assertEquals(Instant.ofEpochMilli(1450773634000L), InstantTimeUtils.getTimestamp("2015-12-22T07:40:34-01:00", FORMATS));

		assertEquals(Instant.ofEpochMilli(1328626291785L), InstantTimeUtils.getTimestamp("2012-02-07T14:51:31.785394+00:00", FORMATS)); //Tue, 07 Feb 2012 14:51:31.785 GMT
		assertEquals(Instant.ofEpochMilli(1328622691785L), InstantTimeUtils.getTimestamp("2012-02-07T14:51:31.785394+01:00", FORMATS));
		assertEquals(Instant.ofEpochMilli(1328629891785L), InstantTimeUtils.getTimestamp("2012-02-07T14:51:31.785394-01:00", FORMATS));
	}

	@Test
	public void overlapsTest() {

		assertTrue(InstantTimeUtils.overlaps(null, null, null, null));

		Instant startA = Instant.ofEpochSecond(5000);
		assertTrue(InstantTimeUtils.overlaps(startA, null, null, null));

		Instant startB = Instant.ofEpochSecond(5000);
		assertTrue(InstantTimeUtils.overlaps(startA, null, startB, null));

		Instant endA = Instant.ofEpochSecond(10000);
		assertTrue(InstantTimeUtils.overlaps(startA, endA, startB, null));

		Instant endB = Instant.ofEpochSecond(8000);
		assertTrue(InstantTimeUtils.overlaps(startA, endA, startB, endB));

		// 2
		startA = Instant.ofEpochSecond(5000);
		endA = Instant.ofEpochSecond(8000);

		startB = Instant.ofEpochSecond(10000);
		endB = Instant.ofEpochSecond(14000);

		assertFalse(InstantTimeUtils.overlaps(startA, endA, startB, endB));
		assertFalse(InstantTimeUtils.overlaps(startB, endB, startA, endA));

		// 3
		startA = Instant.ofEpochSecond(5000);
		endA = Instant.ofEpochSecond(8000);

		startB = Instant.ofEpochSecond(4000);
		endB = Instant.ofEpochSecond(14000);

		assertTrue(InstantTimeUtils.overlaps(startA, endA, startB, endB));
		assertTrue(InstantTimeUtils.overlaps(startB, endB, startA, endA));

		// 4

		startA = Instant.ofEpochSecond(5000);
		endA = Instant.ofEpochSecond(14000);

		startB = Instant.ofEpochSecond(4000);
		endB = Instant.ofEpochSecond(12000);

		assertTrue(InstantTimeUtils.overlaps(startA, endA, startB, endB));
		assertTrue(InstantTimeUtils.overlaps(startB, endB, startA, endA));

		try {
			InstantTimeUtils.overlaps(endA, startA, startB, endB);
			fail();
		}
		catch (IllegalArgumentException e) {
			assertEquals("Start date one can't be after end date one!", e.getMessage());
		}

		try {
			InstantTimeUtils.overlaps(startA, endA, endB, startB);
			fail();
		}
		catch (IllegalArgumentException e) {
			assertEquals("Start date two can't be after end date two!", e.getMessage());
		}
	}


}