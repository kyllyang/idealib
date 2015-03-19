package org.kyll.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public final class StringUtil {
	public static boolean isNull(String str) {
		return str == null;
	}

	public static boolean isNotNull(String str) {
		return !StringUtil.isNull(str);
	}

	public static boolean isEmpty(String str) {
		return StringUtil.isNull(str) || "".equals(str);
	}

	public static boolean isNotEmpty(String str) {
		return !StringUtil.isEmpty(str);
	}

	public static String toFirstUpperCase(String str) {
		return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}

	public static String toFirstLowerCase(String str) {
		return Character.toLowerCase(str.charAt(0)) + str.substring(1);
	}

	public static boolean parseTrue(String str) {
		return StringUtil.isNotEmpty(str) && ("true".equalsIgnoreCase(str) || "yes".equalsIgnoreCase(str) || "on".equalsIgnoreCase(str) || "1".equalsIgnoreCase(str));
	}

	public static boolean parseFalse(String str) {
		return StringUtil.isNotEmpty(str) && ("false".equalsIgnoreCase(str) || "no".equalsIgnoreCase(str) || "off".equalsIgnoreCase(str) || "0".equalsIgnoreCase(str));
	}

	public static int utf8Length(String str) {
		return str.getBytes().length;
	}

	public static String encryptSHA(String value) {
        String str = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(value.getBytes());
            str = bytesToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
        }
        return str;
	}

	public static String encodeUTF8(String str) {
		String result = str;
		try {
			result = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String bytesToHex(byte[] bytes) {
        String des = "";
        for (int i = 0; i < bytes.length; i++) {
            String tmp = (Integer.toHexString(bytes[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

	public static String[] split(String str, String... delimiteds) {
		char c = delimiteds[0].charAt(0);

		int pos = str.indexOf(c);
		if (pos < 0) {
			return new String[]{str};
		}
		List<String> strList = new ArrayList<String>();
		if (pos == 0) {
			strList.add("");
		} else {
			strList.add(str.substring(0, pos));
		}
		while (pos >= 0) {
			int end = str.indexOf(c, pos + 1);
			if (end < 0) {
				end = str.length();
			}
			if (end - pos == 1) {
				strList.add("");
			} else {
				strList.add(str.substring(pos + 1, end));
			}
			pos = str.indexOf(c, pos + 1);
		}
		return strList.toArray(new String[strList.size()]);
	}

	public static Long[] toLongArray(String str) {
		String[] strs = StringUtil.split(str, ",");
		Long[] is = new Long[strs.length];
		for (int i = 0; i < is.length; i++) {
			try {
				is[i] = Long.parseLong(strs[i]);
			} catch (NumberFormatException e) {
				is[i] = null;
			}
		}
		return is;
	}

	public static String ifEmptyValue(String value, String defaultValue) {
		return StringUtil.isEmpty(value) ? defaultValue : value;
	}

	private StringUtil() {
	}
}
