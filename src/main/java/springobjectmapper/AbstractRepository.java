package springobjectmapper;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.util.ReflectionUtils;

import springobjectmapper.dialect.Dialect;
import springobjectmapper.query.IQuery;

public class AbstractRepository<T> {
	private final TableProperties<T> properties;
	private final SimpleJdbcTemplate template;
	private final Dialect dialect;

	protected AbstractRepository() {
		// Default constructor only for creating proxies
		this.properties = null;
		this.template = null;
		this.dialect = null;
	}

	public AbstractRepository(DataSource dataSource, Dialect dialect, final Class<T> typeClass) {
		this.dialect = dialect;
		template = new SimpleJdbcTemplate(dataSource);
		properties = new TableProperties<T>(typeClass);
	}

	public T findById(Long id) {
		return template.queryForObject(properties.parse(dialect.queryById()), properties.mapper(), id);
	}

	public List<T> query(IQuery query) {
		return query(query, 0, 10000);
	}

	public List<T> query(IQuery query, int first, int count) {
		String baseQuery = query.select(dialect);
		String betweenQuery = dialect.selectBetween(baseQuery, first, count);
		String orderedQuery = dialect.appendOrder(betweenQuery, query.order());
		return template.query(properties.parse(orderedQuery), properties.mapper(), query.arguments());
	}

	public int count(IQuery query) {
		return template.queryForInt(properties.parse(query.count(dialect)), query.arguments());
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
