package com.github.jannehietamaki.mapper;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlTemplate {
	private final Pattern pattern = Pattern.compile("\\{(.*?)\\}");
	private final String template;
	private final Map<String, String> values;

	public SqlTemplate(String template, Map<String, String> values) {
		this.template = template;
		this.values = values;
	}

	public String parse() {
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
