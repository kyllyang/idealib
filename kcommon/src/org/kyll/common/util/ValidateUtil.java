package org.kyll.common.util;

public final class ValidateUtil {
	public static boolean length(String str, int min, int max) {
		int length = str.length();
		return length >= min && length <= max;
	}

	public static boolean utf8Length(String str, int min, int max) {
		int length = StringUtil.utf8Length(str);
		return length >= min && length <= max;
	}

	private ValidateUtil() {
	}
}
