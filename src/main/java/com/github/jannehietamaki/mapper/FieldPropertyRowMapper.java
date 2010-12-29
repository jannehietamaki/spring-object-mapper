package com.github.jannehietamaki.mapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import org.objenesis.ObjenesisHelper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.ReflectionUtils;

public class FieldPropertyRowMapper<T> implements RowMapper<T> {
	private final Class<T> typeClass;
	private final Map<String, Field> fieldMapping;

	public FieldPropertyRowMapper(Class<T> typeClass, Field[] fields) {
		this.typeClass = typeClass;
		this.fieldMapping = ReflectionHelper.getFieldMappings(fields);
	}

	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		try {
			T result = newObject(typeClass);
			for (Entry<String, Field> entry : fieldMapping.entrySet()) {
				Object value = rs.getObject(entry.getKey());
				ReflectionUtils.setField(entry.getValue(), result, value);
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private T newObject(Class<T> type) {
		return (T) ObjenesisHelper.newInstance(type);
	}
}
