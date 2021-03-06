package com.zandero.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
  * Utilities to help using date and time
 * @author drejc (Andrej Završnik)
 */
@Deprecated
public final class DateTimeUtils {

	/**
	 * On second in milliseconds
	 */
	public static final long ONE_SECOND = 1000L;

	/**
	 * Ten seconds in milliseconds
	 */
	public static final long TEN_SECONDS = 10_000L;

	/**
	 * On minute in milliseconds
	 */
	public final static long ONE_MINUTE = 60L * ONE_SECOND;

	/**
	 * On hour in milliseconds
	 */
	public final static long ONE_HOUR = 60L * ONE_MINUTE;

	/**
	 * On day in milliseconds
	 */
	public final static long ONE_DAY = 24L * ONE_HOUR;

	private DateTimeUtils() {
		// hiding constructor
	}

	// TODO: Migrate to: DateTimeFormatter
	private static ThreadLocal<SimpleDateFormat> simpleDateFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {

			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	private static ThreadLocal<SimpleDateFormat> simpleTimeFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {

			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
		}
	};

	private static ThreadLocal<SimpleDateFormat> simpleDateTimeFormatThreadLocal = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {

			return new SimpleDateFormat("yyyy-MM-dd HH:mm");
		}
	};

	/**
	 * Prepared date format for yyyy-MM-dd for date only
	 *
	 * @return Simple date format for yyyy-MM-dd
	 */
	public static SimpleDateFormat getDateFormat() {

		return simpleDateFormatThreadLocal.get();
	}

	/**
	 * Prepare date time format (without milliseconds and timezone)
	 *
	 * @return Simple date format for yyy-MM-dd HH:mm
	 */
	public static SimpleDateFormat getDateTimeFormat() {

		return simpleDateTimeFormatThreadLocal.get();
	}

	/**
	 * Prepared full date format
	 * @return Simple date format for yyyy-MM-dd HH:mm:ss Z
	 */
	public static SimpleDateFormat getTimeFormat() {

		return simpleTimeFormatThreadLocal.get();
	}

	/**
	 * @return New UTC calendar instance
	 */
	public static Calendar getCalendar() {

		return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	}

	/**
	 * @param time to be set (UTC)
	 * @return New UTC calendar with time set
	 */
	public static Calendar getCalendar(long time) {

		Calendar calendar = getCalendar();
		calendar.setTimeInMillis(time);
		return calendar;
	}

	/**
	 * Formats time to String
	 * @param time to be formatted (UTC)
	 * @return date time formatted to yyyy-MM-dd HH:mm:ss Z
	 */
	public static String formatDateTime(long time) {

		SimpleDateFormat format = getTimeFormat();
		Calendar calendar = getCalendar(time);

		return format.format(calendar.getTime());
	}

	/**
	 * Formats time to String
	 * @param time to be formatted (UTC)
	 * @return date time formatted to yyyy-MM-dd HH:mm
	 */
	public static String formatDateTimeShort(long time) {

		SimpleDateFormat format = getDateTimeFormat();
		Calendar calendar = getCalendar(time);

		return format.format(calendar.getTime());
	}

	/**
	 * Formats time to date only
	 * @param time to be formatted (UTC)
	 * @return date formatted yyyy-MM-dd
	 */
	public static String formatDate(long time) {

		SimpleDateFormat format = getDateFormat();
		Calendar calendar = getCalendar(time);

		return format.format(calendar.getTime());
	}

	/**
	 * Custom date time format
	 * @param time to be formatted
	 * @param format simple date time format, or null for default yyyy-MM-dd HH:mm:ss Z format
	 * @return formated date time
	 */
	public static String format(long time, SimpleDateFormat format) {

		if (format == null) {
			return formatDateTime(time);
		}

		Calendar calendar = getCalendar(time);
		return format.format(calendar.getTime());
	}

	/**
	 * Returns time for given time zone
	 * @param time current time
	 * @param timezone time zone (-12 / +12)
	 * @return time in other time zone
	 */
	public static long getTimezoneTime(long time, int timezone) {

		Calendar calendar = getCalendar(time);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);

		hour = (hour + timezone) % 24;
		if (hour < 0) {
			hour = 24 + hour;
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}

		calendar.set(Calendar.HOUR_OF_DAY, hour);
		return calendar.getTimeInMillis();
	}

	/**
	 * Converts local hour back to UTC hour
	 *
	 * @param hour     local hour in time zone
	 * @param timezone current time zone
	 * @return UTC hour
	 */
	public static int getUtcHour(int hour, int timezone) {

		hour = (hour - timezone) % 24;
		return (hour < 0) ? 24 + hour : hour;
	}

	/**
	 * Converts UTC hour to time zone hour
	 *
	 * @param hour     UTC hour
	 * @param timezone time zone
	 * @return hour as seen in the given time zone
	 */
	public static int getTimezoneHour(int hour, int timezone) {

		hour = (hour + timezone) % 24;
		return (hour < 0) ? 24 + hour : hour;
	}

	/**
	 * Converts timestamp into OffsetDatetime
	 *
	 * @param timestamp to be converted
	 * @return OffsetDatetime representation of timestamp
	 */
	public static OffsetDateTime toOffsetDateTime(long timestamp) {

		Calendar calendar = DateTimeUtils.getCalendar(timestamp);
		return OffsetDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
	}

	/**
	 * Converts offset date time to long timestamp
	 * @param timestamp to be converted
	 * @return time stamp in milliseconds
	 */
	public static long fromOffsetDateTime(OffsetDateTime timestamp) {

		Assert.notNull(timestamp, "Missing offset time stamp!");
		return timestamp.toInstant().toEpochMilli();
	}

	/**
	 * Gets first millisecond of first day in month
	 *
	 * @param time to get first millisecond
	 * @return first millisecond of month for given time
	 */
	public static long getMonthStart(long time) {

		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC);
		dateTime = dateTime.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).with(ChronoField.MILLI_OF_SECOND, 0);
		return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
	}

	/**
	 * Returns last millisecond of last day in month ... +1 = next first day in month
	 *
	 * @param time to get last second in month
	 * @return last millisecond of month for given time
	 */
	public static long getMonthEnd(long time) {

		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC);
		dateTime = dateTime.withDayOfMonth(1).withHour(23).withMinute(59).withSecond(59).with(ChronoField.MILLI_OF_SECOND, 999);
		dateTime = dateTime.plus(1, ChronoUnit.MONTHS).minus(1, ChronoUnit.DAYS);
		return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
	}

	/**
	 * Returns day in month, where first day of month == 1
	 *
	 * @param time to get day of month
	 * @return 1..31
	 */
	public static int getDayInMonth(long time) {

		return DateTimeUtils.getCalendar(time).get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Gets first millisecond of first day in week
	 *
	 * @param time to get first millisecond (this week)
	 * @return first millisecond of week for given time
	 */
	public static long getWeekStart(long time) {

		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC);
		dateTime = dateTime.with(ChronoField.DAY_OF_WEEK, 1).withHour(0).withMinute(0).withSecond(0).with(ChronoField.MILLI_OF_SECOND, 0);
		return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
	}

	/**
	 * Returns last millisecond of last day in week
	 *
	 * @param time to get last second (this week)
	 * @return last millisecond of week for given time
	 */
	public static long getWeekEnd(long time) {

		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC);
		dateTime = dateTime.with(ChronoField.DAY_OF_WEEK, 7).withHour(23).withMinute(59).withSecond(59).with(ChronoField.MILLI_OF_SECOND, 999);
		return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
	}

	/**
	 * Returns number of day in week, where monday is the first day of the week (1)
	 *
	 * @param time to check day in week
	 * @return 1 - monday, 2 - tuesday ... 7 - saturday
	 */
	public static int getDayInWeek(long time) {

		int dayInWeek = getCalendar(time).get(Calendar.DAY_OF_WEEK) - 1;

		// SUNDAY == 1 - 1 == 0
		if (dayInWeek == 0) {
			dayInWeek = 7; // make sunday the last day of the week
		}

		return dayInWeek;
	}

	/**
	 * @param time to check if it is a weekend day
	 * @return true if Saturday or Sunday, otherwise false
	 */
	public static boolean isWeekend(long time) {

		return getDayInWeek(time) >= 6;
	}

	/**
	 * Removes time component from date time
	 *
	 * @param time to remove hours, minutes ... from
	 * @return 0 millisecond in day
	 */
	public static long getDateOnly(long time) {

		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC);
		dateTime = dateTime.withHour(0).withMinute(0).withSecond(0).with(ChronoField.MILLI_OF_SECOND, 0);
		return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
	}

	/**
	 * Convert any given timestamp string to long
	 * Note that in case conversion fails to produce a result a 0 timestamp is returned!
	 *
	 * @param value   to be converted
	 * @param formats list of formats to try out
	 * @return time stamp or 0 if conversion failed
	 */
	public static long getTimestamp(String value, SimpleDateFormat[] formats) {

		Assert.notNull(formats, "Missing date time formats");
		Assert.isTrue(formats.length > 0, "Missing date time formats");

		if (StringUtils.isNullOrEmptyTrimmed(value)) {
			return 0;
		}

		// OK check if we have 6 digit milliseconds ... if so truncate
		if (value.matches(".*\\.\\d{6}.*")) {
			// remove 3 milliseconds places
			int index = value.indexOf(".");
			value = value.substring(0, index + 4) + value.substring(index + 7);
		}

		// Go over all formats ... and if success return value
		for (SimpleDateFormat format : formats) {

			TimeZone oldDefaultTimeZone = TimeZone.getDefault();

			try {
				// make sure we are in the correct time zone while doing this
				TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

				Date date = format.parse(value);
				return date.getTime();
			}
			catch (ParseException e) {
				// try next one ...
			}
			finally {
				TimeZone.setDefault(oldDefaultTimeZone);
			}
		}

		return 0;
	}

	/**
	 * Check is time periods overlap
	 * @param startOne start time of first period / null for full
	 * @param endOne end tme of first period / null for full
	 * @param startTwo start time of second period / null for full
	 * @param endTwo end time of second period / null for full
	 * @return true if periods overlap, false if they don't
	 * @throws IllegalArgumentException in case start time is after end time of first/second period
	 */
	public static boolean overlaps(Instant startOne, Instant endOne, Instant startTwo, Instant endTwo) {

		return InstantTimeUtils.overlaps(startOne, endOne, startTwo, endTwo);
	}
}