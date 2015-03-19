package org.kyll.common.prompt;

import org.kyll.common.util.StringUtil;

import java.io.Serializable;

public class Prompt implements Serializable {
	private String level;
	private String content;
	private StringBuilder paramValues;

	public Prompt() {
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getParamValues() {
		if (paramValues == null) {
			return "";
		}
		if (paramValues.lastIndexOf(",") == paramValues.length()) {
			return paramValues.substring(0, paramValues.length() - 1);
		}
		return paramValues.toString();
	}

	public void setParamValues(String paramValues) {
		this.paramValues = new StringBuilder(StringUtil.ifEmptyValue(paramValues, ""));
	}

	public void addParamValue(String value) {
		if (paramValues == null) {
			paramValues = new StringBuilder();
		}
		paramValues.append(value).append(',');
	}
}
