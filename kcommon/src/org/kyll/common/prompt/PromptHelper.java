package org.kyll.common.prompt;

import org.kyll.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public final class PromptHelper {
	public static final String PARAM_PROMPTLIST = "promptList";
	public static final String LEVEL_INFO = "info";
	public static final String LEVEL_WARN = "warn";
	public static final String LEVEL_ERROR = "error";

	public static List<Prompt> toList(Prompt... prompts) {
		List<Prompt> promptList = new ArrayList<Prompt>();
		for (Prompt prompt : prompts) {
			promptList.add(prompt);
		}
		return promptList;
	}

	public static String makeParam(Prompt... prompts) {
		String str = "";
		for (int i = 0; i < prompts.length; i++) {
			str += "promptList[" + i + "].level=" + prompts[i].getLevel() + "&promptList[" + i + "].content=" + prompts[i].getContent() + "&promptList[" + i + "].paramValues=" + StringUtil.encodeUTF8(prompts[i].getParamValues()) + "&";
		}
		if (str.length() > 0) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	public static Prompt makePrompt(String level, String content, String... paramValues) {
		Prompt prompt = new Prompt();
		prompt.setLevel(level);
		prompt.setContent(content);
		for (String paramValue : paramValues) {
			prompt.addParamValue(paramValue);
		}
		return prompt;
	}

	public static boolean isInfo(Prompt prompt) {
		return LEVEL_INFO.equals(prompt.getLevel());
	}

	public static boolean isWarn(Prompt prompt) {
		return LEVEL_WARN.equals(prompt.getLevel());
	}

	public static boolean isError(Prompt prompt) {
		return LEVEL_ERROR.equals(prompt.getLevel());
	}

	private PromptHelper() {
	}
}
