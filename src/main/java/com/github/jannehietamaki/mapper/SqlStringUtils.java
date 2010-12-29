package com.github.jannehietamaki.mapper;

import java.util.List;

public class SqlStringUtils {

	public static String updates(List<String> fieldNames) {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (String field : fieldNames) {
			if (!first) {
				result.append(",");
			}
			result.append(field);
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

}
