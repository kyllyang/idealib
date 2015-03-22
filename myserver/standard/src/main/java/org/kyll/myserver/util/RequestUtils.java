package org.kyll.myserver.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.kyll.myserver.base.paginated.Paginated;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.*;

/**
 * User: Kyll
 * Date: 2014-11-08 08:39
 */
public class RequestUtils {
	public static Paginated getPaginated(HttpServletRequest request) {
		Paginated paginated = new Paginated();
		paginated.setStartRecord(NumberUtils.toInt(request.getParameter("start"), Paginated.DEFAULT_STARTRECORD));
		paginated.setMaxRecord(NumberUtils.toInt(request.getParameter("limit"), Paginated.DEFAULT_MAXRECORD));

		String sort = request.getParameter("sort");
		if (StringUtils.isNotBlank(sort)) {
			JSONArray ja = JSONArray.fromObject(sort);
			List<Paginated.Sort> sortList = new ArrayList<>();
			for (int i = 0; i < ja.size(); i++) {
				JSONObject jo = ja.getJSONObject(0);
				Paginated.Sort paginatedSort = paginated.new Sort();
				paginatedSort.setProperty(jo.getString("property"));
				paginatedSort.setDirection(jo.getString("direction"));
				sortList.add(paginatedSort);
			}
			paginated.setSortList(sortList);
		}
		return paginated;
	}

	public static <T> T get(HttpServletRequest request, String prefix, Class<T> clazz) {
		T t = newInstance(clazz);
		if (t != null) {
			Map<String, Object> requestParameterMap = new HashMap<>();
			requestParameterMap.putAll(request.getParameterMap());
			Set<String> keySet = requestParameterMap.keySet();

			keySet.stream().filter(paramName -> paramName.startsWith(prefix + ".")).forEach(paramName -> fillValueToBean(t, nextPropertyName(paramName), requestParameterMap.get(paramName)));
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	private static void fillValueToBean(Object bean, String paramName, Object value) {
		if (hasNextPropertyName(paramName)) {
			String currentPropName = currentProptertyName(paramName);
			Object getValue = invokeGetter(bean, removeCollectionFeature(currentPropName));
			if (getValue == null) {
				Method method = getGetterMethod(bean, removeCollectionFeature(currentPropName));
				Class returnClazz = method.getReturnType();
				ClazzType returnClazzType = clazzToClazzType(returnClazz);
				if (ClazzType.ARRAY == returnClazzType) {
					Type returnType = method.getGenericReturnType();
					Class genericClass = clazzToGenericClazz(returnType);

					getValue = newInstance(genericClass);

					int index = collectionPropertyIndex(currentPropName);
					Object[] vs = new Object[index + 1];
					vs[index] = getValue;

					Method setterMethod = getSetterMethod(bean, removeCollectionFeature(currentPropName), returnClazz);
					try {
						setterMethod.invoke(bean, new Object[]{vs});
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				} else if (ClazzType.LIST == returnClazzType) {
					Type returnType = method.getGenericReturnType();
					Class genericClass = clazzToGenericClazz(returnType);

					getValue = newInstance(genericClass);

					int index = collectionPropertyIndex(currentPropName);
					List vs = new ArrayList();
					if (index == -1) {
						vs.add(getValue);
					} else {
						for (int i = 0; i < index; i++) {
							vs.add(null);
						}
						vs.add(getValue);
					}

					Method setterMethod = getSetterMethod(bean, removeCollectionFeature(currentPropName), returnClazz);
					try {
						setterMethod.invoke(bean, vs);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				} else if (ClazzType.SET == returnClazzType) {
					Type returnType = method.getGenericReturnType();
					Class genericClass = clazzToGenericClazz(returnType);

					getValue = newInstance(genericClass);

					Set vs = new HashSet();
					vs.add(getValue);

					Method setterMethod = getSetterMethod(bean, removeCollectionFeature(currentPropName), returnClazz);
					try {
						setterMethod.invoke(bean, vs);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				} else if (ClazzType.MAP == returnClazzType) {
					Type returnType = method.getGenericReturnType();
					Class genericClass = clazzToGenericClazz(returnType);

					getValue = newInstance(genericClass);

					String key = collectionPropertyKey(currentPropName);
					Map vs = new HashMap();
					vs.put(key, getValue);

					Method setterMethod = getSetterMethod(bean, removeCollectionFeature(currentPropName), returnClazz);
					try {
						setterMethod.invoke(bean, vs);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				} else {
					getValue = newInstance(returnClazz);

					Method setterMethod = getSetterMethod(bean, removeCollectionFeature(currentPropName), returnClazz);
					try {
						setterMethod.invoke(bean, getValue);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			} else {
				Method method = getGetterMethod(bean, removeCollectionFeature(currentPropName));
				Class returnClazz = method.getReturnType();
				ClazzType returnClazzType = clazzToClazzType(returnClazz);
				if (ClazzType.ARRAY == returnClazzType) {
					Object[] getValues = (Object[]) getValue;
					int index = collectionPropertyIndex(currentPropName);
					if (index < getValues.length) {
						getValue = getValues[index];
					} else {
						Object[] newValues = new Object[index + 1];
						System.arraycopy(getValues, 0, newValues, 0, getValues.length);

						Type returnType = method.getGenericReturnType();
						Class genericClass = clazzToGenericClazz(returnType);

						getValue = newInstance(genericClass);
						newValues[index] = getValue;

						Method setterMethod = getSetterMethod(bean, removeCollectionFeature(currentPropName), returnClazz);
						try {
							setterMethod.invoke(bean, new Object[]{newValues});
						} catch (IllegalAccessException | InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				} else if (ClazzType.LIST == returnClazzType) {
					List getValues = (List) getValue;
					int gvs = getValues.size();
					int index = collectionPropertyIndex(currentPropName);
					if (index == -1) {
						getValue = getValues.get(gvs - 1);
					} else {
						Type returnType = method.getGenericReturnType();
						Class genericClass = clazzToGenericClazz(returnType);

						if (index < gvs) {
							getValue = getValues.get(index);
							if (getValue == null) {
								getValue = newInstance(genericClass);
								getValues.set(index, getValue);
							}
						} else {
							for (int i = gvs ; i < index; i++) {
								getValues.add(null);
							}

							getValue = newInstance(genericClass);
							getValues.add(getValue);
						}
					}
				} else if (ClazzType.SET == returnClazzType) {
					Set getValues = (Set) getValue;
					getValue = getValues.iterator().next();
				} else if (ClazzType.MAP == returnClazzType) {
					Map getValues = (Map) getValue;
					String key = collectionPropertyKey(currentPropName);
					getValue = getValues.get(key);
					if (getValue == null) {
						Type returnType = method.getGenericReturnType();
						Class genericClass = clazzToGenericClazz(returnType);

						getValue = newInstance(genericClass);
						getValues.put(key, getValue);
					}
				}
			}
			String nextPropName = nextPropertyName(paramName);
			fillValueToBean(getValue, nextPropName, value);
		} else {
			invokeSetter(bean, paramName, value);
		}
	}

	private static <T> T newInstance(Class<T> t) {
		try {
			return t.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static boolean hasNextPropertyName(String paramName) {
		return paramName.indexOf('.') > 0;
	}

	private static String currentProptertyName(String paramName) {
		int pos = paramName.indexOf('.');
		return pos == -1 ? paramName : paramName.substring(0, pos);
	}

	private static String nextPropertyName(String paramName) {
		int pos = paramName.indexOf('.');
		return pos == -1 ? null : paramName.substring(pos + 1);
	}

	private static Method getGetterMethod(Object bean, String propName) {
		try {
			return bean.getClass().getMethod(BEAN_GET_METHOD_PREFIX + StringUtils.capitalize(propName));
		} catch (NoSuchMethodException e) {
			try {
				return bean.getClass().getMethod(BEAN_IS_METHOD_PREFIX + StringUtils.capitalize(propName));
			} catch (NoSuchMethodException e1) {
				return null;
			}
		}
	}

	private static Method getSetterMethod(Object bean, String propName, Class clazz) {
		try {
			return bean.getClass().getMethod(BEAN_SET_METHOD_PREFIX + StringUtils.capitalize(propName), clazz);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	private static Object invokeGetter(Object bean, String propName) {
		Method getterMethod = getGetterMethod(bean, propName);
		if (getterMethod != null) {
			try {
				return getterMethod.invoke(bean);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static void invokeSetter(Object bean, String propName, Object value) {
		String removedCollectionPropName = removeCollectionFeature(propName);
		Method getterMethod = getGetterMethod(bean, removedCollectionPropName);
		if (getterMethod == null) {
			return;
		}
		Class returnType = getterMethod.getReturnType();
		ClazzType fieldClazzType = clazzToClazzType(returnType);
		Method method = getSetterMethod(bean, removedCollectionPropName, returnType);
		if (ClazzType.BYTE == fieldClazzType) {
			String v = firstValue(value);
			try {
				method.invoke(bean, StringUtils.isEmpty(v) ? null : Byte.parseByte(v));
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.SHORT == fieldClazzType) {
			String v = firstValue(value);
			try {
				method.invoke(bean, StringUtils.isEmpty(v) ? null : Short.parseShort(v));
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.INT == fieldClazzType) {
			String v = firstValue(value);
			try {
				method.invoke(bean, StringUtils.isEmpty(v) ? null : Integer.parseInt(v));
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.LONG == fieldClazzType) {
			String v = firstValue(value);
			try {
				method.invoke(bean, StringUtils.isEmpty(v) ? null : Long.parseLong(v));
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.FLOAT == fieldClazzType) {
			String v = firstValue(value);
			try {
				method.invoke(bean, StringUtils.isEmpty(v) ? null : Float.parseFloat(v));
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.DOUBLE == fieldClazzType) {
			String v = firstValue(value);
			try {
				method.invoke(bean, StringUtils.isEmpty(v) ? null : Double.parseDouble(v));
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.CHAR == fieldClazzType) {
			String v = firstValue(value);
			try {
				method.invoke(bean, StringUtils.isEmpty(v) ? null : v.charAt(0));
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.BOOLEAN == fieldClazzType) {
			String v = firstValue(value);
			try {
				method.invoke(bean, StringUtils.isEmpty(v) ? null : Boolean.parseBoolean(v));
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.STRING == fieldClazzType) {
			String v = firstValue(value);
			try {
				method.invoke(bean, StringUtils.isEmpty(v) ? null : v);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.DATE == fieldClazzType) {
			String v = firstValue(value);
			try {
				try {
					method.invoke(bean, StringUtils.isEmpty(v) ? null : DateUtils.parseDate(v, new String[]{toStandardFormat(v)}));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.OBJECT == fieldClazzType) {
			try {
				method.invoke(bean, value);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.BYTE_ARRAY == fieldClazzType) {
			if (value instanceof CommonsMultipartFile) {
				CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) value;

				byte[] uploadFile = commonsMultipartFile.getBytes();
				if (uploadFile.length == 0) {
					uploadFile = null;
				}
				try {
					method.invoke(bean, new Object[]{uploadFile});
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}

				Method fileNameMethod = getSetterMethod(bean, removedCollectionPropName + "FileName", String.class);
				if (fileNameMethod != null) {
					String fileName  = commonsMultipartFile.getOriginalFilename();
					try {
						fileNameMethod.invoke(bean, fileName);
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			} else {
				int index = collectionPropertyIndex(propName);
				Object returnValue = null;
				try {
					returnValue = getterMethod.invoke(bean);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				if (returnValue == null) {
					returnValue = new Byte[index + 1];
				}
				Byte[] vs = (Byte[]) returnValue;
				if (vs.length <= index) {
					Byte[] tmps = new Byte[index + 1];
					System.arraycopy(vs, 0, tmps, 0, vs.length);
					vs = tmps;
				}
				String v = firstValue(value);
				vs[index] = StringUtils.isEmpty(v) ? null : Byte.parseByte(v);
				try {
					method.invoke(bean, new Object[]{vs});
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		} else if (ClazzType.SHORT_ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = null;
			try {
				returnValue = getterMethod.invoke(bean);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (returnValue == null) {
				returnValue = new Short[index + 1];
			}
			Short[] vs = (Short[]) returnValue;
			if (vs.length <= index) {
				Short[] tmps = new Short[index + 1];
				System.arraycopy(vs, 0, tmps, 0, vs.length);
				vs = tmps;
			}
			String v = firstValue(value);
			vs[index] = StringUtils.isEmpty(v) ? null : Short.parseShort(v);
			try {
				method.invoke(bean, new Object[]{vs});
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.INT_ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = null;
			try {
				returnValue = getterMethod.invoke(bean);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (returnValue == null) {
				returnValue = new Integer[index + 1];
			}
			Integer[] vs = (Integer[]) returnValue;
			if (vs.length <= index) {
				Integer[] tmps = new Integer[index + 1];
				System.arraycopy(vs, 0, tmps, 0, vs.length);
				vs = tmps;
			}
			String v = firstValue(value);
			vs[index] = StringUtils.isEmpty(v) ? null : Integer.parseInt(v);
			try {
				method.invoke(bean, new Object[]{vs});
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.LONG_ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = null;
			try {
				returnValue = getterMethod.invoke(bean);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (returnValue == null) {
				returnValue = new Long[index + 1];
			}
			Long[] vs = (Long[]) returnValue;
			if (vs.length <= index) {
				Long[] tmps = new Long[index + 1];
				System.arraycopy(vs, 0, tmps, 0, vs.length);
				vs = tmps;
			}
			String v = firstValue(value);
			vs[index] = StringUtils.isEmpty(v) ? null : Long.parseLong(v);
			try {
				method.invoke(bean, new Object[]{vs});
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.FLOAT_ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = null;
			try {
				returnValue = getterMethod.invoke(bean);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (returnValue == null) {
				returnValue = new Float[index + 1];
			}
			Float[] vs = (Float[]) returnValue;
			if (vs.length <= index) {
				Float[] tmps = new Float[index + 1];
				System.arraycopy(vs, 0, tmps, 0, vs.length);
				vs = tmps;
			}
			String v = firstValue(value);
			vs[index] = StringUtils.isEmpty(v) ? null : Float.parseFloat(v);
			try {
				method.invoke(bean, new Object[]{vs});
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.DOUBLE_ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = null;
			try {
				returnValue = getterMethod.invoke(bean);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (returnValue == null) {
				returnValue = new Double[index + 1];
			}
			Double[] vs = (Double[]) returnValue;
			if (vs.length <= index) {
				Double[] tmps = new Double[index + 1];
				System.arraycopy(vs, 0, tmps, 0, vs.length);
				vs = tmps;
			}
			String v = firstValue(value);
			vs[index] = StringUtils.isEmpty(v) ? null : Double.parseDouble(v);
			try {
				method.invoke(bean, new Object[]{vs});
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.CHAR_ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = null;
			try {
				returnValue = getterMethod.invoke(bean);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (returnValue == null) {
				returnValue = new Character[index + 1];
			}
			Character[] vs = (Character[]) returnValue;
			if (vs.length <= index) {
				Character[] tmps = new Character[index + 1];
				System.arraycopy(vs, 0, tmps, 0, vs.length);
				vs = tmps;
			}
			String v = firstValue(value);
			vs[index] = StringUtils.isEmpty(v) ? null : v.charAt(0);
			try {
				method.invoke(bean, new Object[]{vs});
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.BOOLEAN_ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = null;
			try {
				returnValue = getterMethod.invoke(bean);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (returnValue == null) {
				returnValue = new Boolean[index + 1];
			}
			Boolean[] vs = (Boolean[]) returnValue;
			if (vs.length <= index) {
				Boolean[] tmps = new Boolean[index + 1];
				System.arraycopy(vs, 0, tmps, 0, vs.length);
				vs = tmps;
			}
			String v = firstValue(value);
			vs[index] = StringUtils.isEmpty(v) ? null : Boolean.parseBoolean(v);
			try {
				method.invoke(bean, new Object[]{vs});
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.STRING_ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = null;
			try {
				returnValue = getterMethod.invoke(bean);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (returnValue == null) {
				returnValue = new String[index + 1];
			}
			String[] vs = (String[]) returnValue;
			if (vs.length <= index) {
				String[] tmps = new String[index + 1];
				System.arraycopy(vs, 0, tmps, 0, vs.length);
				vs = tmps;
			}
			String v = firstValue(value);
			vs[index] = StringUtils.isEmpty(v) ? null : v;
			try {
				method.invoke(bean, new Object[]{vs});
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = null;
			try {
				returnValue = getterMethod.invoke(bean);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (returnValue == null) {
				returnValue = new Object[index + 1];
			}
			Object[] vs = (Object[]) returnValue;
			if (vs.length <= index) {
				Object[] tmps = new Object[index + 1];
				System.arraycopy(vs, 0, tmps, 0, vs.length);
				vs = tmps;
			}
			vs[index] = value;
			try {
				method.invoke(bean, new Object[]{vs});
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.LIST == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = null;
			try {
				returnValue = getterMethod.invoke(bean);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (returnValue == null) {
				returnValue = new ArrayList();
			}
			List vs = (List) returnValue;
			if (index == -1) {
				vs.add(value);
			} else {
				vs.add(index, value);
			}
			try {
				method.invoke(bean, vs);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.SET == fieldClazzType) {
			Object returnValue = null;
			try {
				returnValue = getterMethod.invoke(bean);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (returnValue == null) {
				returnValue = new HashSet();
			}
			Set vs = (Set) returnValue;
			vs.add(value);
			try {
				method.invoke(bean, vs);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		} else if (ClazzType.MAP == fieldClazzType) {
			String key = collectionPropertyKey(propName);
			Object returnValue = null;
			try {
				returnValue = getterMethod.invoke(bean);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			if (returnValue == null) {
				returnValue = new HashMap();
			}
			Map vs = (Map) returnValue;
			vs.put(key, value);
			try {
				method.invoke(bean, vs);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	private static int collectionPropertyIndex(String paramName) {
		int lp = paramName.indexOf('[');
		if (lp == -1) {
			return -1;
		}
		int rp = paramName.indexOf(']');
		if (rp == -1) {
			return -1;
		}
		return Integer.parseInt(paramName.substring(lp + 1, rp));
	}

	private static String collectionPropertyKey(String paramName) {
		int lp = paramName.indexOf('\'');
		if (lp == -1) {
			return null;
		}
		int rp = paramName.indexOf('\'');
		if (rp == -1) {
			return null;
		}
		return paramName.substring(lp, rp);
	}

	private static String removeCollectionFeature(String propName) {
		int pos = propName.indexOf('[');
		return pos == -1 ? propName : propName.substring(0, pos);
	}

	private static Class clazzToGenericClazz(Type type) {
		String str = type.toString();
		if (str.contains(" L"))  {
			str = str.substring(str.indexOf(" L") + 2, str.indexOf(';'));
			try {
				return Class.forName(str);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else if (str.contains("<") && str.endsWith(">")) {
			str = str.substring(str.indexOf('<') + 1, str.lastIndexOf('>'));
			if (str.contains(",")) {
				str = split(str, ",")[1].trim();
			}
			try {
				return Class.forName(str);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			throw new UnsupportedOperationException("Unsupported operation: " + type);
		}
		return null;
	}

	private static ClazzType clazzToClazzType(String str) {
		ClazzType ct = null;
		if (str.startsWith("class ")) {
			str = str.substring(6);
			if ("java.lang.Byte".equals(str)) {
				ct = ClazzType.BYTE;
			} else if ("java.lang.Short".equals(str)) {
				ct = ClazzType.SHORT;
			} else if ("java.lang.Integer".equals(str)) {
				ct = ClazzType.INT;
			} else if ("java.lang.Long".equals(str)) {
				ct = ClazzType.LONG;
			} else if ("java.lang.Float".equals(str)) {
				ct = ClazzType.FLOAT;
			} else if ("java.lang.Double".equals(str)) {
				ct = ClazzType.DOUBLE;
			} else if ("java.lang.Character".equals(str)) {
				ct = ClazzType.CHAR;
			} else if ("java.lang.Boolean".equals(str)) {
				ct = ClazzType.BOOLEAN;
			} else if ("java.lang.String".equals(str)) {
				ct = ClazzType.STRING;
			} else if ("java.util.Date".equals(str)) {
				ct = ClazzType.DATE;
			} else if ("org.springframework.web.multipart.commons.CommonsMultipartFile".equals(str)) {
				ct = ClazzType.UPLOADFILE;
			} else if ("java.util.List".equals(str)) {
				ct = ClazzType.LIST;
			} else if ("java.util.Set".equals(str)) {
				ct = ClazzType.SET;
			} else if ("java.util.Map".equals(str)) {
				ct = ClazzType.MAP;
			} else if ("[B".equals(str)) {
				ct = ClazzType.BYTE_ARRAY;
			} else if (str.startsWith("[Ljava.lang.Byte")) {
				ct = ClazzType.BYTE_ARRAY;
			} else if (str.startsWith("[Ljava.lang.Short")) {
				ct = ClazzType.SHORT_ARRAY;
			} else if (str.startsWith("[Ljava.lang.Integer")) {
				ct = ClazzType.INT_ARRAY;
			} else if (str.startsWith("[Ljava.lang.Long")) {
				ct = ClazzType.LONG_ARRAY;
			} else if (str.startsWith("[Ljava.lang.Float")) {
				ct = ClazzType.FLOAT_ARRAY;
			} else if (str.startsWith("[Ljava.lang.Double")) {
				ct = ClazzType.DOUBLE_ARRAY;
			} else if (str.startsWith("[Ljava.lang.Character")) {
				ct = ClazzType.CHAR_ARRAY;
			} else if (str.startsWith("[Ljava.lang.Boolean")) {
				ct = ClazzType.BOOLEAN_ARRAY;
			} else if (str.startsWith("[Ljava.lang.String")) {
				ct = ClazzType.STRING_ARRAY;
			} else if (str.startsWith("[L")){
				ct = ClazzType.ARRAY;
			} else {
				ct = ClazzType.OBJECT;
			}
		} else if (str.startsWith("interface ")) {
			str = str.substring(10);
			if ("java.util.List".equals(str)) {
				ct = ClazzType.LIST;
			} else if ("java.util.Set".equals(str)) {
				ct = ClazzType.SET;
			} else if ("java.util.Map".equals(str)) {
				ct = ClazzType.MAP;
			}
		} else if ("byte".equals(str)) {
			ct = ClazzType.BYTE;
		} else if ("short".equals(str)) {
			ct = ClazzType.SHORT;
		} else if ("int".equals(str)) {
			ct = ClazzType.INT;
		} else if ("long".equals(str)) {
			ct = ClazzType.LONG;
		} else if ("float".equals(str)) {
			ct = ClazzType.FLOAT;
		} else if ("double".equals(str)) {
			ct = ClazzType.DOUBLE;
		} else if ("char".equals(str)) {
			ct = ClazzType.CHAR;
		} else if ("boolean".equals(str)) {
			ct = ClazzType.BOOLEAN;
		}
		return ct;
	}

	private static ClazzType clazzToClazzType(Class clazz) {
		return clazzToClazzType(clazz.toString());
	}

	private static String firstValue(Object value) {
		if (value == null) {
			return null;
		}
		ClazzType ct = clazzToClazzType(value.getClass());
		if (ClazzType.STRING_ARRAY == ct) {
			Object[] vs = (Object[]) value;
			return vs.length == 0 ? "" : String.valueOf(vs[0]);
		}
		throw new UnsupportedOperationException("Unsupported operation: " + value);
	}

	private static String[] split(String str, String... delimiteds) {
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

	private static String toStandardFormat(String str) {
		if (str.matches(REGEX_DATE)) {
			return str + " 00:00:00";
		}
		if (str.matches(REGEX_TIME)) {
			return "0000-00-00 " + str;
		}
		if (str.matches(REGEX_DATETIME)) {
			return str;
		}
		throw new IllegalArgumentException(str + " is not standard date format");
	}

	private static final String REGEX_DATE = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
	private static final String REGEX_TIME = "^[0-9]{2}:[0-9]{2}:[0-9]{2}$";
	private static final String REGEX_DATETIME = "^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$";

	private static final String BEAN_SET_METHOD_PREFIX = "set";
	private static final String BEAN_GET_METHOD_PREFIX = "get";
	private static final String BEAN_IS_METHOD_PREFIX = "is";

	private enum ClazzType {
		BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, CHAR, BOOLEAN, STRING, DATE, UPLOADFILE, OBJECT,
		BYTE_ARRAY, SHORT_ARRAY, INT_ARRAY, LONG_ARRAY, FLOAT_ARRAY, DOUBLE_ARRAY, CHAR_ARRAY, BOOLEAN_ARRAY, STRING_ARRAY,
		ARRAY, LIST, SET, MAP
	}

	private RequestUtils() {
	}
}
