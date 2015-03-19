package org.kyll.common.util;

import com.wutka.jox.JOXBeanOutputStream;
import com.wutka.jox.JOXConfig;
import org.kyll.common.Const;
import org.kyll.common.base.Vo;
import org.kyll.common.type.ClazzType;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class BeanUtil {
	public static Long getId(Vo vo) {
		return vo == null ? null : vo.getId();
	}

	public static boolean isEmpty(Vo vo) {
		return BeanUtil.getId(vo) == null;
	}

	public static boolean isNotEmpty(Vo vo) {
		return !BeanUtil.isEmpty(vo);
	}

	public static String toXmlString(Object... objects) {
		ByteArrayOutputStream xmlOut = new ByteArrayOutputStream();
		JOXBeanOutputStream objectOut = new JOXBeanOutputStream(xmlOut);
		JOXConfig config = objectOut.getConfig();
		config.setEncoding("UTF-8");
		config.setDateFormat(DateUtil.DATETIME_FORMAT);
		config.setWriteClassNames(false);
		config.setAtomsAsAttributes(false);
		try {
			for (Object object : objects) {
				objectOut.writeObject(StringUtil.toFirstLowerCase(object.getClass().getSimpleName()), object);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return XmlUtil.clearXmlHeader(xmlOut.toString());
		} finally {
			try {
				objectOut.close();
				xmlOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String toXmlString(List<?> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("<response>");
		sb.append(BeanUtil.toXmlString(list.toArray()));
		sb.append("</response>");
		return sb.toString();
	}

	public static boolean isEL(String str) {
		return str.matches("^\\$\\{.+\\}$");
	}

	public static String elContent(String str) {
		return str.substring(2, str.length() - 1);
	}

	public static <T> T newInstance(Class<T> t) {
		try {
			return t.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Method getGetterMethod(Object bean, String propName) {
		try {
			return bean.getClass().getMethod(Const.BEAN_GET_METHOD_PREFIX + StringUtil.toFirstUpperCase(propName));
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	public static Method getSetterMethod(Object bean, String propName, Class clazz) {
		try {
			return bean.getClass().getMethod(Const.BEAN_SET_METHOD_PREFIX + StringUtil.toFirstUpperCase(propName), clazz);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	public static Object invokeGetter(Object bean, String propName) throws Exception {
		Method getterMethod = getGetterMethod(bean, propName);
		return getterMethod == null ? null : getterMethod.invoke(bean);
	}

	@SuppressWarnings("unchecked")
	public static void invokeSetter(Object bean, String propName, Object value) throws Exception {
		String removedCollectionPropName = removeCollectionFeature(propName);
		Method getterMethod = getGetterMethod(bean, removedCollectionPropName);
		if (getterMethod == null) {
			return;
		}
		Class returnType = getterMethod.getReturnType();
		ClazzType fieldClazzType = clazzToClazzType(returnType);
		Method method = getSetterMethod(bean, removedCollectionPropName, returnType);
		if (method == null) {
			throw new Exception("bean miss setter method: " + removedCollectionPropName);
		}
		if (ClazzType.BYTE == fieldClazzType) {
			String v = firstValue(value);
			method.invoke(bean, StringUtil.isEmpty(v) ? null : Byte.parseByte(v));
		} else if (ClazzType.SHORT == fieldClazzType) {
			String v = firstValue(value);
			method.invoke(bean, StringUtil.isEmpty(v) ? null : Short.parseShort(v));
		} else if (ClazzType.INT == fieldClazzType) {
			String v = firstValue(value);
			method.invoke(bean, StringUtil.isEmpty(v) ? null : Integer.parseInt(v));
		} else if (ClazzType.LONG == fieldClazzType) {
			String v = firstValue(value);
			method.invoke(bean, StringUtil.isEmpty(v) ? null : Long.parseLong(v));
		} else if (ClazzType.FLOAT == fieldClazzType) {
			String v = firstValue(value);
			method.invoke(bean, StringUtil.isEmpty(v) ? null : Float.parseFloat(v));
		} else if (ClazzType.DOUBLE == fieldClazzType) {
			String v = firstValue(value);
			method.invoke(bean, StringUtil.isEmpty(v) ? null : Double.parseDouble(v));
		} else if (ClazzType.CHAR == fieldClazzType) {
			String v = firstValue(value);
			method.invoke(bean, StringUtil.isEmpty(v) ? null : v.charAt(0));
		} else if (ClazzType.BOOLEAN == fieldClazzType) {
			String v = firstValue(value);
			method.invoke(bean, StringUtil.isEmpty(v) ? null : Boolean.parseBoolean(v));
		} else if (ClazzType.STRING == fieldClazzType) {
			String v = firstValue(value);
			method.invoke(bean, StringUtil.isEmpty(v) ? null : v);
		} else if (ClazzType.DATE == fieldClazzType) {
			String v = firstValue(value);
			method.invoke(bean, StringUtil.isEmpty(v) ? null : DateUtil.parseDatetime(DateUtil.toStandardFormat(v)));
		} else if (ClazzType.OBJECT == fieldClazzType) {
			method.invoke(bean, value);
		} else if (ClazzType.BYTE_ARRAY == fieldClazzType) {
			if (value instanceof CommonsMultipartFile) {
				CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) value;

				byte[] uploadFile = commonsMultipartFile.getBytes();
				if (uploadFile.length == 0) {
					uploadFile = null;
				}
				method.invoke(bean, new Object[]{uploadFile});

				Method fileNameMethod = getSetterMethod(bean, removedCollectionPropName + "FileName", String.class);
				if (fileNameMethod != null) {
					String fileName  = commonsMultipartFile.getOriginalFilename();
					fileNameMethod.invoke(bean, fileName);
				}
			} else {
				int index = collectionPropertyIndex(propName);
				Object returnValue = getterMethod.invoke(bean);
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
				vs[index] = StringUtil.isEmpty(v) ? null : Byte.parseByte(v);
				method.invoke(bean, new Object[]{vs});
			}
		} else if (ClazzType.SHORT_ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = getterMethod.invoke(bean);
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
			vs[index] = StringUtil.isEmpty(v) ? null : Short.parseShort(v);
			method.invoke(bean, new Object[]{vs});
		} else if (ClazzType.INT_ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = getterMethod.invoke(bean);
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
			vs[index] = StringUtil.isEmpty(v) ? null : Integer.parseInt(v);
			method.invoke(bean, new Object[]{vs});
		} else if (ClazzType.LONG_ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = getterMethod.invoke(bean);
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
			vs[index] = StringUtil.isEmpty(v) ? null : Long.parseLong(v);
			method.invoke(bean, new Object[]{vs});
		} else if (ClazzType.FLOAT_ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = getterMethod.invoke(bean);
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
			vs[index] = StringUtil.isEmpty(v) ? null : Float.parseFloat(v);
			method.invoke(bean, new Object[]{vs});
		} else if (ClazzType.DOUBLE_ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = getterMethod.invoke(bean);
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
			vs[index] = StringUtil.isEmpty(v) ? null : Double.parseDouble(v);
			method.invoke(bean, new Object[]{vs});
		} else if (ClazzType.CHAR_ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = getterMethod.invoke(bean);
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
			vs[index] = StringUtil.isEmpty(v) ? null : v.charAt(0);
			method.invoke(bean, new Object[]{vs});
		} else if (ClazzType.BOOLEAN_ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = getterMethod.invoke(bean);
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
			vs[index] = StringUtil.isEmpty(v) ? null : Boolean.parseBoolean(v);
			method.invoke(bean, new Object[]{vs});
		} else if (ClazzType.STRING_ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = getterMethod.invoke(bean);
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
			vs[index] = StringUtil.isEmpty(v) ? null : v;
			method.invoke(bean, new Object[]{vs});
		} else if (ClazzType.ARRAY == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = getterMethod.invoke(bean);
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
			method.invoke(bean, new Object[]{vs});
		} else if (ClazzType.LIST == fieldClazzType) {
			int index = collectionPropertyIndex(propName);
			Object returnValue = getterMethod.invoke(bean);
			if (returnValue == null) {
				returnValue = new ArrayList();
			}
			List vs = (List) returnValue;
			if (index == -1) {
				vs.add(value);
			} else {
				vs.add(index, value);
			}
			method.invoke(bean, vs);
		} else if (ClazzType.SET == fieldClazzType) {
			Object returnValue = getterMethod.invoke(bean);
			if (returnValue == null) {
				returnValue = new HashSet();
			}
			Set vs = (Set) returnValue;
			vs.add(value);
			method.invoke(bean, vs);
		} else if (ClazzType.MAP == fieldClazzType) {
			String key = collectionPropertyKey(propName);
			Object returnValue = getterMethod.invoke(bean);
			if (returnValue == null) {
				returnValue = new HashMap();
			}
			Map vs = (Map) returnValue;
			vs.put(key, value);
			method.invoke(bean, vs);
		}
	}

	public static ClazzType clazzToClazzType(Class clazz) {
		return clazzToClazzType(clazz.toString());
	}

	public static ClazzType clazzToClazzType(Type type) {
		return clazzToClazzType(type.toString());
	}

	public static Class clazzToGenericClazz(Type type) throws Exception {
		String str = type.toString();
		if (str.contains(" L"))  {
			str = str.substring(str.indexOf(" L") + 2, str.indexOf(';'));
			return Class.forName(str);
		} else if (str.contains("<") && str.endsWith(">")) {
			str = str.substring(str.indexOf('<') + 1, str.lastIndexOf('>'));
			if (str.contains(",")) {
				str = StringUtil.split(str, ",")[1].trim();
			}
			return Class.forName(str);
		} else {
			throw new UnsupportedOperationException("Unsupported operation: " + type);
		}
	}

	public static Class clazzToGenericClazzForMapKey(Type type) throws Exception {
		String str = type.toString();
		if (str.contains("<") && str.endsWith(">")) {
			str = str.substring(str.indexOf('<') + 1, str.lastIndexOf('>'));
			str = StringUtil.split(str, ",")[0].trim();
			return Class.forName(str);
		} else {
			throw new UnsupportedOperationException("Unsupported operation: " + type);
		}
	}

	public static Class clazzToGenericClazzForMapValue(Type type) throws Exception {
		String str = type.toString();
		if (str.contains("<") && str.endsWith(">")) {
			str = str.substring(str.indexOf('<') + 1, str.lastIndexOf('>'));
			str = StringUtil.split(str, ",")[1].trim();
			return Class.forName(str);
		} else {
			throw new UnsupportedOperationException("Unsupported operation: " + type);
		}
	}

	public static int collectionPropertyIndex(String paramName) {
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

	public static String collectionPropertyKey(String paramName) {
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

	public static String removeCollectionFeature(String propName) {
		int pos = propName.indexOf('[');
		return pos == -1 ? propName : propName.substring(0, pos);
	}

	public static boolean hasNextPropertyName(String paramName) {
		return paramName.indexOf('.') > 0;
	}

	public static String nextPropertyName(String paramName) {
		int pos = paramName.indexOf('.');
		return pos == -1 ? null : paramName.substring(pos + 1);
	}

	public static String currentProptertyName(String paramName) {
		int pos = paramName.indexOf('.');
		return pos == -1 ? paramName : paramName.substring(0, pos);
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

	private BeanUtil() {
	}
}
