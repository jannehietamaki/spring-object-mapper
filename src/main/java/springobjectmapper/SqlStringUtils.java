package springobjectmapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SqlStringUtils {

	public static String updates(List<Field> fields) {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (Field field : fields) {
			if (!first) {
				result.append(",");
			}
			result.append(SqlStringUtils.fieldName(field));
			result.append("=?");
			first = false;
		}
		return result.toString();
	}

	public static String placeHolders(int length) {
		StringBuilder result = new StringBuilder();
		for (int a = 0; a < length; a++) {
			if (a != 0) {
				result.append(",");
			}
			result.append('?');
		}
		return result.toString();
	}

	public static List<String> getFieldNames(List<Field> fields) {
		List<String> results = new ArrayList<String>();
		for (Field field : fields) {
			results.add(toDbFieldName(field.getName()));
		}
		return results;
	}

	private static String toDbFieldName(String value) {
		StringBuilder result = new StringBuilder();
		for (char ch : value.toCharArray()) {
			if (Character.isUpperCase(ch)) {
				if (result.length() > 0) {
					result.append("_");
				}
				result.append(Character.toLowerCase(ch));
			} else {
				result.append(ch);
			}
		}
		return result.toString();
	}

	public static String tableName(Class<?> c) {
		String[] items = c.getCanonicalName().split("\\.");
		return toDbFieldName(items[items.length - 1]);
	}

	public static String fieldName(Field field) {
		return toDbFieldName(field.getName());
	}

}
