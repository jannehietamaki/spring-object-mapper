/*
 * Copyright 2010 Janne Hietamaki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
		throw new RuntimeException("Field with annotation " + annotationType.getCanonicalName() + " was not found on type " + c.getName() + "!");
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
