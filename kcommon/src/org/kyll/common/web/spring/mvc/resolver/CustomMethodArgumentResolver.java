package org.kyll.common.web.spring.mvc.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.kyll.common.web.util.RequestUtil;
import org.springframework.core.MethodParameter;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * User: Kyll
 * Date: 2009-7-27
 * Time: 14:39:41
 */
public class CustomMethodArgumentResolver implements WebArgumentResolver {
	@SuppressWarnings("unchecked")
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		Class clazz = methodParameter.getParameterType();
		if (clazz == ModelMap.class || clazz == HttpServletRequest.class || clazz == HttpServletResponse.class || clazz == HttpSession.class) {
			return UNRESOLVED;
		}
		return RequestUtil.valueOfBean(webRequest, methodParameter.getParameterName(), clazz, methodParameter.getMethod().getGenericParameterTypes()[methodParameter.getParameterIndex()]);
	}
}
