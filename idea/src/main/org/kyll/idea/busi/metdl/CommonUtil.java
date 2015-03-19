package org.kyll.idea.busi.metdl;

public final class CommonUtil {
	public static void main(String[] args) {
		String s = "input type=\"hidden\" name=\"op\" value=\"download1\"";

		getHtmlTagValueByAttributeName(s, "name");
		System.out.println(s);
	}

	public static String getHtmlTagValueByAttributeName(String tagStr, String attrName) {
		if (tagStr == null || tagStr.trim().equals("")) {
			return null;
		}
		// + 2: ="
		try {
			tagStr = tagStr.substring(tagStr.indexOf(attrName) + attrName.length() + 2);
			return tagStr.substring(0, tagStr.indexOf('"'));
		} catch (Exception e) {
			return null;
		}
	}
}
