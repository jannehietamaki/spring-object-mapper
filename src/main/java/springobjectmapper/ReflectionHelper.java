package springobjectmapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ReflectionUtils;

public class ReflectionHelper {
	public static Object[] getValues(List<Field> fields, Object o) {
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

	public static Field getAnnotatedField(Class<?> c, Class<? extends Annotation> annotationType) {
		for (Field field : getFields(c)) {
			Annotation annotation = field.getAnnotation(annotationType);
			if (annotation != null) {
				return field;
			}
		}
		throw new RuntimeException("Field with annotation " + annotationType.getCanonicalName() + " was not found!");
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

	public static Object getFieldValue(Field field, Object o) {
		try {
			return field.get(o);
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
			return null;
		}
	}

	public static List<Field> getFields(Class<?> c) {
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
		return results;
	}

	private static boolean isEntityField(Field field) {
		return !field.isSynthetic() && !Modifier.isStatic(field.getModifiers());
	}

}
