package com.github.jannehietamaki.mapper;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.util.ReflectionUtils;

import com.github.jannehietamaki.mapper.dialect.Dialect;

public class AbstractRepository<T> {
	private final TableProperties<T> properties;
	private final SimpleJdbcTemplate template;
	private final Dialect dialect;

	public AbstractRepository(DataSource dataSource, Dialect dialect, final Class<T> typeClass) {
		this.dialect = dialect;
		template = new SimpleJdbcTemplate(dataSource);
		properties = new TableProperties<T>(typeClass);
	}

	public T findById(Long id) {
		return template.queryForObject(properties.parse(dialect.queryById()), properties.mapper(), id);
	}

	public List<T> query(String sql, Object... args) {
		return template.query(properties.parse(sql), properties.mapper(), args);
	}

	public List<T> all() {
		return query("SELECT * FROM {table}");
	}

	public void save(T entity) {
		if (!properties.isIdSet(entity)) {
			insert(entity);
		} else {
			update(entity);
		}
	}

	public void update(final T entity) {
		if (template.update(properties.parse(dialect.update()), properties.valuesOf(entity)) != 1) {
			throw new RuntimeException("Update failed!");
		}
	}

	public void insert(final T entity) {
		template.update(properties.parse(dialect.insert()), properties.valuesOf(entity));
		Object id = template.queryForObject(dialect.getInsertedId(), properties.idField().getType());
		ReflectionUtils.setField(properties.idField(), entity, id);
	}
}
