package com.github.jannehietamaki.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.ReflectionUtils;

public class ReflectionHelper {
	public static Object[] getValues(Field[] fields, Object o) {
		List<Object> results = new ArrayList<Object>();
		for (Field field : fields) {
			try {
				results.add(field.get(o));
			} catch (Exception e) {
				ReflectionUtils.handleReflectionException(e);
			}
		}
		return results.toArray();
	}

	public static Field getField(Class<?> c, String name) {
		do {
			try {
				Field field = c.getDeclaredField(name);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException ex) {
			} catch (Exception e) {
				ReflectionUtils.handleReflectionException(e);
			}
			c = c.getSuperclass();
		} while (c != null);
		throw new RuntimeException("Field " + name + " was not found!");
	}

	public static List<String> getFieldNames(Class<?> c) {
		List<String> results = new ArrayList<String>();
		for (Field field : getFields(c)) {
			results.add(toDbFieldName(field.getName()));
		}
		return results;
	}

	private static String toDbFieldName(String value) {
		StringBuilder result = new StringBuilder();
		for (char ch : value.toCharArray()) {
			if (Character.isUpperCase(ch)) {
				result.append("_");
				result.append(Character.toLowerCase(ch));
			} else {
				result.append(ch);
			}
		}
		return result.toString();
	}

	public static Field[] getFields(Class<?> c) {
		List<Field> results = new ArrayList<Field>();
		do {
			Field[] fields = c.getDeclaredFields();
			for (Field field : fields) {
				if (isEntityField(field)) {
					results.add(field);
				}
				field.setAccessible(true);
			}
			c = c.getSuperclass();
		} while (c != null);
		return results.toArray(new Field[results.size()]);
	}

	private static boolean isEntityField(Field field) {
		return !field.isSynthetic() && !Modifier.isStatic(field.getModifiers());
	}

	public static Map<String, Field> getFieldMappings(Field[] fields) {
		Map<String, Field> result = new HashMap<String, Field>();
		for (Field field : fields) {
			result.put(toDbFieldName(field.getName()), field);
		}
		return result;
	}
}
