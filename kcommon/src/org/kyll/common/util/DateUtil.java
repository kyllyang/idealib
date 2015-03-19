package org.kyll.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {
	public static final String DAY_BEGINTIME = "00:00:00";
	public static final String DAY_ENDTIME = "23:59:59";
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final String PATTERN_TIME = "HH:mm:ss";
	public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

	public static final String REGEX_DATE = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
	public static final String REGEX_TIME = "^[0-9]{2}:[0-9]{2}:[0-9]{2}$";
	public static final String REGEX_DATETIME = "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$";

	public static final SimpleDateFormat FORMAT = new SimpleDateFormat();
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String toStandardFormat(String str) {
		if (str.matches(DateUtil.REGEX_DATE)) {
			return str + " 00:00:00";
		}
		if (str.matches(DateUtil.REGEX_TIME)) {
			return "0000-00-00 " + str;
		}
		if (str.matches(DateUtil.REGEX_DATETIME)) {
			return str;
		}
		throw new IllegalArgumentException(str + " is not standard date format");
	}

	public static Date parseDate(String str) {
		return DateUtil.parseDate(str, DateUtil.PATTERN_DATE);
	}

	public static Date parseTime(String str) {
		return DateUtil.parseDate(str, DateUtil.PATTERN_TIME);
	}

	public static Date parseDatetime(String str) {
		return DateUtil.parseDate(str, DateUtil.PATTERN_DATETIME);
	}

	public static Date parseDate(String str, String pattern) {
		DateUtil.FORMAT.applyPattern(pattern);
		Date date = null;
		try {
			date = DateUtil.FORMAT.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			FORMAT.applyPattern("");
		}
		return date;
	}

	public static String formatDate(Date date) {
		return DateUtil.formatDate(date, DateUtil.PATTERN_DATE);
	}

	public static String formatTime(Date date) {
		return DateUtil.formatDate(date, DateUtil.PATTERN_TIME);
	}

	public static String formatDatetime(Date date) {
		return DateUtil.formatDate(date, DateUtil.PATTERN_DATETIME);
	}

	public static String formatDate(Date date, String pattern) {
		DateUtil.FORMAT.applyPattern(pattern);
		return DateUtil.FORMAT.format(date);
	}

	public static String getCurrentDate() {
		return DateUtil.DATE_FORMAT.format(new Date());
	}

	public static String getCurrentTime() {
		return DateUtil.TIME_FORMAT.format(new Date());
	}

	public static String getCurrentDatetime() {
		return DateUtil.DATETIME_FORMAT.format(new Date());
	}

	public static Date currentDatetime() {
		return new Date();
	}

	public static Date beforeCurrentDate(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Can't be negative number: " + amount);
		}
		return DateUtil.calculateTime(new Date(), Calendar.DAY_OF_MONTH, -1 * amount);
	}

	public static Date afterCurrentDate(int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Can't be negative number: " + amount);
		}
		return DateUtil.calculateTime(new Date(), Calendar.DAY_OF_MONTH, amount);
	}

	public static Date calculateTime(Date date, int field, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(field, amount);
		return c.getTime();
	}

	private DateUtil() {
	}
}
