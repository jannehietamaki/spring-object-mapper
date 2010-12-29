package com.github.jannehietamaki.mapper;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlTemplate {
	private final Pattern pattern = Pattern.compile("\\{(.*?)\\}");
	private final Map<String, String> values;

	public SqlTemplate(Map<String, String> values) {
		this.values = values;
	}

	public String parse(String template) {
		StringBuffer result = new StringBuffer();
		Matcher matcher = pattern.matcher(template);
		while (matcher.find()) {
			String key = matcher.group(1);
			String value = values.get(key);
			if (value == null) {
				throw new IllegalArgumentException("Unable to find value for key " + key);
			}
			matcher.appendReplacement(result, value);
		}
		matcher.appendTail(result);
		return result.toString();
	}
}
