package springobjectmapper.query;

public class OrderedQuery extends AbstractQuery {
	private final Order order;

	public OrderedQuery(Order order) {
		this.order = order;
	}

	@Override
	public Order order() {
		return order;
	}

}
