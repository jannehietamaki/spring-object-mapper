package springobjectmapper.query;

import springobjectmapper.dialect.Dialect;

public class SimpleQuery extends AbstractQuery {
	private final String sql;
	private final Object[] args;
	private final Order order;

	public SimpleQuery(String sql, Order order, Object... args) {
		this.sql = sql;
		this.args = args;
		this.order = order;
	}

	public SimpleQuery(String sql, Object... args) {
		this.sql = sql;
		this.args = args;
		this.order = Order.ANY;
	}

	@Override
	public String select(Dialect dialect) {
		return dialect.select(sql);
	}

	@Override
	public String count(Dialect dialect) {
		return dialect.count(sql);
	}

	@Override
	public Object[] arguments() {
		return args;
	}

	@Override
	public Order order() {
		return order;
	}
}
