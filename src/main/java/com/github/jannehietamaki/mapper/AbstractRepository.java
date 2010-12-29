package com.github.jannehietamaki.mapper;

import static com.github.jannehietamaki.mapper.ReflectionHelper.getField;
import static com.github.jannehietamaki.mapper.ReflectionHelper.getFieldNames;
import static com.github.jannehietamaki.mapper.ReflectionHelper.getFields;
import static com.github.jannehietamaki.mapper.ReflectionHelper.getValues;

import static com.github.jannehietamaki.mapper.SqlStringUtils.placeHolders;
import static com.github.jannehietamaki.mapper.SqlStringUtils.updates;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.objenesis.ObjenesisHelper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import com.github.jannehietamaki.mapper.dialect.Dialect;

public class AbstractRepository<T extends AbstractModel> {
	private static final String idFieldName = "id";
	private final RowMapper<T> mapper;
	private final SimpleJdbcTemplate template;
	private final Dialect dialect;
	private final Map<String, String> tags;
	private final Field[] fields;
	private final Field idField;

	public AbstractRepository(DataSource dataSource, Dialect dialect, final Class<T> typeClass) {
		this.dialect = dialect;
		template = new SimpleJdbcTemplate(dataSource);
		final List<String> fieldNames = getFieldNames(typeClass);
		final List<String> fieldNamesWithoutId = getFieldNames(typeClass);
		fieldNamesWithoutId.remove(idFieldName);
		final T example = createExample(typeClass);
		fields = getFields(typeClass);
		mapper = new FieldPropertyRowMapper<T>(typeClass, fields);
		idField = getField(typeClass, idFieldName);
		tags = new HashMap<String, String>() {
			{
				put("idField", idFieldName);
				put("table", example.tableName());
				put("fields", StringUtils.arrayToDelimitedString(fieldNames.toArray(), ","));
				put("values", placeHolders(fieldNames.size()));
				put("updates", updates(fieldNamesWithoutId));
			}
		};
	}

	@SuppressWarnings("unchecked")
	private T createExample(final Class<T> type) {
		return (T) ObjenesisHelper.newInstance(type);
	}

	public T findById(Long id) {
		return template.queryForObject(parse(dialect.queryById()), mapper, id);
	}

	public List<T> query(String sql, Object... args) {
		return template.query(parse(sql), mapper, args);
	}

	public List<T> all() {
		return query("SELECT * FROM {table}");
	}

	public void save(T entity) {
		if (entity.id() == null) {
			insert(entity);
		} else {
			update(entity);
		}
	}

	public void update(final T entity) {
		Object[] values = getValues(fields, entity);
		if (template.update(parse(dialect.update()), values) != 1) {
			throw new RuntimeException("Update failed!");
		}
	}

	public void insert(final T entity) {
		Object[] values = getValues(fields, entity);
		template.update(parse(dialect.insert()), values);
		Long id = template.queryForLong(dialect.getInsertedId());
		ReflectionUtils.setField(idField, entity, id);
	}

	private String parse(String sql) {
		return new SqlTemplate(sql, tags).parse();
	}
}
