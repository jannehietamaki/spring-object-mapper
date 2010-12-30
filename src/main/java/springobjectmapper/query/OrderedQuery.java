package springobjectmapper.query;

public class OrderedQuery extends Query {

	public OrderedQuery(Order order) {
		super("", order);
	}
}
