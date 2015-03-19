package org.kyll.common.helper;

import java.util.HashMap;
import java.util.Map;

public final class ParamHelper {
	private Map<String, Object> param;

	public static ParamHelper getParamHelper() {
		return new ParamHelper();
	}

	public Map<String, Object> addParam(String name, Object value) {
		param.put(name, value);
		return param;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	private ParamHelper() {
		this.param = new HashMap<String, Object>();
	}
}
