package org.kyll.common.web.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;
import org.apache.taglibs.standard.tag.common.fmt.MessageSupport;
import org.kyll.common.util.StringUtil;

public final class TagUtil {
	public static void print(String str, PageContext pageContext) throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print(str);
		} catch (IOException e) {
			throw new JspException(e);
		}
	}

	public static String getI18NMessage(Tag t, PageContext pageContext, String key, Object[] args) {
		LocalizationContext locCtxt;
		String prefix = null;
		if (t != null) {
			BundleSupport parent = (BundleSupport) t;
			locCtxt = parent.getLocalizationContext();
			prefix = parent.getPrefix();
		} else {
			locCtxt = BundleSupport.getLocalizationContext(pageContext);
		}
		String message = MessageSupport.UNDEFINED_KEY + key + MessageSupport.UNDEFINED_KEY;
		if (locCtxt != null) {
			ResourceBundle bundle = locCtxt.getResourceBundle();
			if (bundle != null) {
				try {
					if (prefix != null) {
						key = prefix + key;
					}
					message = bundle.getString(key);
					if (args != null) {
						MessageFormat formatter = new MessageFormat("");
						if (locCtxt.getLocale() != null) {
							formatter.setLocale(locCtxt.getLocale());
						}
						formatter.applyPattern(message);
						message = formatter.format(args);
					}
				} catch (MissingResourceException mre) {
					message = MessageSupport.UNDEFINED_KEY + key + MessageSupport.UNDEFINED_KEY;
				}
			}
		}
		return message;
	}

	public static String convertToParameter(String name, String value, String prefix) {
		return prefix + name + "=" + (StringUtil.isEmpty(value) ? "" : value);
	}

	public static String convertToParameter(String name, Integer value, String prefix) {
		return TagUtil.convertToParameter(name, String.valueOf(value), prefix);
	}

	public static String convertToHtmlProperty(String name, String value) {
		return StringUtil.isEmpty(value) ? "" : (" " + name + "=\"" + value + "\"");
	}

	public static String convertToHtmlProperty(String name, boolean value) {
		return value ? (" " + name + "=\"" + name + "\"") : "";
	}

	public static String converToCssProperty(String name, String value) {
		return StringUtil.isEmpty(value) ? "" : (name + ": " + value + ";");
	}

	public static String convertToScriptMethod(String name, String... params) {
		if (name == null || "".equals(name)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(name).append('(');
		for (String param : params) {
			sb.append(param).append(',');
		}
		if (sb.lastIndexOf(",") == sb.length() - 1) {
			sb.substring(0, sb.length() - 1);
		}
		sb.append(");");
		return sb.toString();
	}

	private TagUtil() {
	}
}
