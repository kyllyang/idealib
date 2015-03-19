package org.kyll.common.web.util;

import org.kyll.common.type.ClazzType;
import org.kyll.common.util.BeanUtil;
import org.kyll.common.util.StringUtil;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class RequestUtil {
	@SuppressWarnings("unchecked")
	public static Object valueOfBean(WebRequest request, String parameterName, Class clazz, Type type) throws Exception {
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.putAll(request.getParameterMap());
		if (request instanceof ServletWebRequest) {
			ServletWebRequest servletWebRequest = (ServletWebRequest) request;
			Object requestObject = servletWebRequest.getNativeRequest();
			if (requestObject instanceof DefaultMultipartHttpServletRequest) {
				DefaultMultipartHttpServletRequest multipartRequest = (DefaultMultipartHttpServletRequest) requestObject;
				requestMap.putAll(multipartRequest.getFileMap());
			}
		}
		Set<String> keySet = requestMap.keySet();

		Object bean = null;
		ClazzType clazzType = BeanUtil.clazzToClazzType(clazz);
		if (ClazzType.LIST == clazzType) {
			List list = new ArrayList();
			Class genericClass = BeanUtil.clazzToGenericClazz(type);

			for (String paramName : keySet) {
				if (paramName.startsWith(parameterName + "[")) {
					int index = BeanUtil.collectionPropertyIndex(paramName);
					Object object;
					if (index < list.size()) {
						object = list.get(index);
					} else {
						object = BeanUtil.newInstance(genericClass);
						for (int i = list.size(); i < index; i++) {
							list.add(null);
						}
						list.add(object);
					}
					if (object == null) {
						object = BeanUtil.newInstance(genericClass);
						list.set(index, object);
					}

					fillValueToBean(object, BeanUtil.nextPropertyName(paramName), requestMap.get(paramName));
				}
			}

			bean = list;
		} else if (ClazzType.MAP == clazzType) {
			Map<String, Object> map = new HashMap<>();
			Class keyGenericClass = BeanUtil.clazzToGenericClazzForMapKey(type);
			Class valueGenericClass = BeanUtil.clazzToGenericClazzForMapValue(type);

			if (keyGenericClass == String.class && valueGenericClass == Object.class) {
				Object key = null;
				Object value = null;
				for (String paramName : keySet) {
					if (paramName.endsWith(".key")) {
						key = requestMap.get(paramName);
						continue;
					}
					if (paramName.endsWith(".value")) {
						value = requestMap.get(paramName);
						continue;
					}
				}

				if (key != null && value != null) {
					if (key instanceof String) {
						String k = (String) key;
						if (StringUtil.isNotEmpty(k)) {
							map.put(k, value);
						}
					} else if (key instanceof String[] && value instanceof Object[]) {
						String[] keys = (String[]) key;
						Object[] values = (Object[]) value;
						for (int i = 0; i < keys.length; i++) {
							if (StringUtil.isNotEmpty(keys[i])) {
								map.put(keys[i], values[i]);
							}
						}
					}
				}
			}

			bean = map;
		} else {
			try {
				bean = clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			for (String paramName : keySet) {
				if (paramName.startsWith(parameterName + ".")) {
					fillValueToBean(bean, BeanUtil.nextPropertyName(paramName), requestMap.get(paramName));
				}
			}
		}

		return bean;
	}

	@SuppressWarnings("unchecked")
	private static void fillValueToBean(Object bean, String paramName, Object value) throws Exception {
		if (BeanUtil.hasNextPropertyName(paramName)) {
			String currentPropName = BeanUtil.currentProptertyName(paramName);
			Object getValue = BeanUtil.invokeGetter(bean, BeanUtil.removeCollectionFeature(currentPropName));
			if (getValue == null) {
				Method method = BeanUtil.getGetterMethod(bean, BeanUtil.removeCollectionFeature(currentPropName));
				Class returnClazz = method.getReturnType();
				ClazzType returnClazzType = BeanUtil.clazzToClazzType(returnClazz);
				if (ClazzType.ARRAY == returnClazzType) {
					Type returnType = method.getGenericReturnType();
					Class genericClass = BeanUtil.clazzToGenericClazz(returnType);

					getValue = BeanUtil.newInstance(genericClass);

					int index = BeanUtil.collectionPropertyIndex(currentPropName);
					Object[] vs = new Object[index + 1];
					vs[index] = getValue;

					Method setterMethod = BeanUtil.getSetterMethod(bean, BeanUtil.removeCollectionFeature(currentPropName), returnClazz);
					setterMethod.invoke(bean, new Object[]{vs});
				} else if (ClazzType.LIST == returnClazzType) {
					Type returnType = method.getGenericReturnType();
					Class genericClass = BeanUtil.clazzToGenericClazz(returnType);

					getValue = BeanUtil.newInstance(genericClass);

					int index = BeanUtil.collectionPropertyIndex(currentPropName);
					List vs = new ArrayList();
					if (index == -1) {
						vs.add(getValue);
					} else {
						for (int i = 0; i < index; i++) {
							vs.add(null);
						}
						vs.add(getValue);
					}

					Method setterMethod = BeanUtil.getSetterMethod(bean, BeanUtil.removeCollectionFeature(currentPropName), returnClazz);
					setterMethod.invoke(bean, vs);
				} else if (ClazzType.SET == returnClazzType) {
					Type returnType = method.getGenericReturnType();
					Class genericClass = BeanUtil.clazzToGenericClazz(returnType);

					getValue = BeanUtil.newInstance(genericClass);

					Set vs = new HashSet();
					vs.add(getValue);

					Method setterMethod = BeanUtil.getSetterMethod(bean, BeanUtil.removeCollectionFeature(currentPropName), returnClazz);
					setterMethod.invoke(bean, vs);
				} else if (ClazzType.MAP == returnClazzType) {
					Type returnType = method.getGenericReturnType();
					Class genericClass = BeanUtil.clazzToGenericClazz(returnType);

					getValue = BeanUtil.newInstance(genericClass);

					String key = BeanUtil.collectionPropertyKey(currentPropName);
					Map vs = new HashMap();
					vs.put(key, getValue);

					Method setterMethod = BeanUtil.getSetterMethod(bean, BeanUtil.removeCollectionFeature(currentPropName), returnClazz);
					setterMethod.invoke(bean, vs);
				} else {
					getValue = BeanUtil.newInstance(returnClazz);

					Method setterMethod = BeanUtil.getSetterMethod(bean, BeanUtil.removeCollectionFeature(currentPropName), returnClazz);
					setterMethod.invoke(bean, getValue);
				}
			} else {
				Method method = BeanUtil.getGetterMethod(bean, BeanUtil.removeCollectionFeature(currentPropName));
				Class returnClazz = method.getReturnType();
				ClazzType returnClazzType = BeanUtil.clazzToClazzType(returnClazz);
				if (ClazzType.ARRAY == returnClazzType) {
					Object[] getValues = (Object[]) getValue;
					int index = BeanUtil.collectionPropertyIndex(currentPropName);
					if (index < getValues.length) {
						getValue = getValues[index];
					} else {
						Object[] newValues = new Object[index + 1];
						System.arraycopy(getValues, 0, newValues, 0, getValues.length);

						Type returnType = method.getGenericReturnType();
						Class genericClass = BeanUtil.clazzToGenericClazz(returnType);

						getValue = BeanUtil.newInstance(genericClass);
						newValues[index] = getValue;

						Method setterMethod = BeanUtil.getSetterMethod(bean, BeanUtil.removeCollectionFeature(currentPropName), returnClazz);
						setterMethod.invoke(bean, new Object[]{newValues});
					}
				} else if (ClazzType.LIST == returnClazzType) {
					List getValues = (List) getValue;
					int gvs = getValues.size();
					int index = BeanUtil.collectionPropertyIndex(currentPropName);
					if (index == -1) {
						getValue = getValues.get(gvs - 1);
					} else {
						Type returnType = method.getGenericReturnType();
						Class genericClass = BeanUtil.clazzToGenericClazz(returnType);

						if (index < gvs) {
							getValue = getValues.get(index);
							if (getValue == null) {
								getValue = BeanUtil.newInstance(genericClass);
								getValues.set(index, getValue);
							}
						} else {
							for (int i = gvs ; i < index; i++) {
								getValues.add(null);
							}

							getValue = BeanUtil.newInstance(genericClass);
							getValues.add(getValue);
						}
					}
				} else if (ClazzType.SET == returnClazzType) {
					Set getValues = (Set) getValue;
					getValue = getValues.iterator().next();
				} else if (ClazzType.MAP == returnClazzType) {
					Map getValues = (Map) getValue;
					String key = BeanUtil.collectionPropertyKey(currentPropName);
					getValue = getValues.get(key);
					if (getValue == null) {
						Type returnType = method.getGenericReturnType();
						Class genericClass = BeanUtil.clazzToGenericClazz(returnType);

						getValue = BeanUtil.newInstance(genericClass);
						getValues.put(key, getValue);
					}
				}
			}
			String nextPropName = BeanUtil.nextPropertyName(paramName);
			fillValueToBean(getValue, nextPropName, value);
		} else {
			BeanUtil.invokeSetter(bean, paramName, value);
		}
	}
}
