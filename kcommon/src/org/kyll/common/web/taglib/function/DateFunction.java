package org.kyll.common.web.taglib.function;

import org.kyll.common.util.DateUtil;

import java.util.Date;

public class DateFunction {
	public static Date currentDatetime() {
		return DateUtil.currentDatetime();
	}

	public static Date beforeCurrentDate(int amount) {
		return DateUtil.beforeCurrentDate(amount);
	}

	public static Date afterCurrentDate(int amount) {
		return DateUtil.afterCurrentDate(amount);
	}
}
