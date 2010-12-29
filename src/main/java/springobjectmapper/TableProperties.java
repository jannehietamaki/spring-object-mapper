package springobjectmapper;

import static springobjectmapper.ReflectionHelper.*;
import static springobjectmapper.SqlStringUtils.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

public class TableProperties<T> {

	private final List<Field> allFields;
	private final List<Field> updateFields;
	private final Field idField;
	private final RowMapper<T> mapper;
	private final SqlTemplate sqlTemplate;

	public TableProperties(final Class<T> typeClass) {
		idField = getAnnotatedField(typeClass, Id.class);
		updateFields = getUpdateFields(typeClass);
		allFields = getUpdateFields(typeClass);
		allFields.add(idField); // idField is always the last one

		final List<String> fieldNames = getFieldNames(allFields);

		Map<String, String> tags = new HashMap<String, String>();
		tags.put("idField", fieldName(idField));
		tags.put("table", tableName(typeClass));
		tags.put("fields", StringUtils.arrayToDelimitedString(fieldNames.toArray(), ","));
		tags.put("values", placeHolders(fieldNames.size()));
		tags.put("updates", updates(updateFields));
		sqlTemplate = new SqlTemplate(tags);
		mapper = new FieldPropertyRowMapper<T>(typeClass, this);
	}

	private List<Field> getUpdateFields(final Class<T> typeClass) {
		List<Field> fields = getFields(typeClass);
		fields.remove(idField);
		return fields;
	}

	public String parse(String sql) {
		return sqlTemplate.parse(sql);
	}

	public RowMapper<T> mapper() {
		return mapper;
	}

	public Object[] valuesOf(T entity) {
		return getValues(allFields, entity);
	}

	public boolean isIdSet(T entity) {
		return getFieldValue(idField, entity) != null;
	}

	public Field idField() {
		return idField;
	}

	public List<Field> allFields() {
		return allFields;
	}
}
