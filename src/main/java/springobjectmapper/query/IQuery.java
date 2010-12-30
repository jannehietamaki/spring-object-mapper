package springobjectmapper.query;

import java.io.Serializable;

import springobjectmapper.dialect.Dialect;

public interface IQuery extends Serializable {

	public final static IQuery ALL = new EmptyQuery();

	String select(Dialect dialect);

	String count(Dialect dialect);

	Object[] arguments();

	Order order();
}
