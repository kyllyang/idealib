package org.kyll.common.web.taglib.function;

import java.util.Calendar;

public class FormatFunction {
	public static String formatDurationInMillis(Long millis) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);

		year -= 1970;
		month -= 1;
		day -= 1;
		hour -= 8;

		String result = "";
		if (year > 0) {
			result += year + "-";
		}
		result += toStr(year, month, "-", "", "");
		result += toStr(month, day, " ", "", "");
		result += toStr(day, hour, ":", "00", "");
		result += toStr(hour, minute, ":", "00", "");
		result += toStr(minute, second, "", "", "00");
		return result;
	}

	private static String toStr(int a, int b, String c, String d, String e) {
		String s = "00" + b;
		s = s.substring(s.length() - 2);

		String r = "";
		if (b > 0) {
			r += s + c;
		} else if (a > 0) {
			r += "00" + c;
		} else if (d.length() > 0) {
			r += d + c;
		} else if (e.length() > 0) {
			r += e;
		}

		return r;
	}
}
