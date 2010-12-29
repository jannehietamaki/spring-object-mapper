package springobjectmapper.query;

import springobjectmapper.dialect.Dialect;

public interface Query {

	public final static Query ALL = new EmptyQuery();

	String select(Dialect dialect);

	String count(Dialect dialect);

	Object[] arguments();

	Order order();
}
