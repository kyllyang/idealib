package org.kyll.myserver.util;

import org.apache.commons.beanutils.BeanUtils;
import org.kyll.myserver.base.BaseService;
import org.kyll.myserver.base.Vo;

import java.lang.reflect.InvocationTargetException;

/**
 * User: Kyll
 * Date: 2014-11-08 12:57
 */
public class EntityUtils {
	public static <T, P> T convert(Vo<P> vo, Class<T> clazz, BaseService<T, P> baseService) {
		T t = null;
		if (vo != null) {
			P p = vo.getId();
			if (p == null) {
				try {
					t = clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			} else {
				t = baseService.get(p);
			}

			try {
				BeanUtils.copyProperties(t, vo);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return t;
	}

	private EntityUtils() {
	}
}
