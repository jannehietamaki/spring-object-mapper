package springobjectmapper.query;

import springobjectmapper.dialect.Dialect;

public abstract class AbstractQuery implements Query {
	@Override
	public String select(Dialect dialect) {
		return dialect.select("");
	}

	@Override
	public String count(Dialect dialect) {
		return dialect.count("");
	}

	@Override
	public Object[] arguments() {
		return new Object[0];
	}

	@Override
	public Order order() {
		return Order.ANY;
	}
}