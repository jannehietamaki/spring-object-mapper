package springobjectmapper.query;

import springobjectmapper.dialect.Dialect;

public class SqlQuery implements IQuery {
	private final String sql;
	private final Object[] arguments;

	public SqlQuery(String sql, Object... arguments) {
		this.sql = sql;
		this.arguments = arguments;
	}

	@Override
	public String count(Dialect dialect) {
		throw new UnsupportedOperationException("SqlQuery does not support count yet");
	}

	@Override
	public String select(Dialect dialect) {
		return sql;
	}

	@Override
	public Object[] arguments() {
		return arguments;
	}

	@Override
	public Order order() {
		return Order.ANY;
	}
}
