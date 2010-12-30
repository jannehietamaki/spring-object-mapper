package springobjectmapper.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
	public final static Order ANY = new Order();

	List<OrderItem> orders = new ArrayList<OrderItem>();

	private Order() {
	}

	public Order and(String key) {
		orders.add(new OrderItem(key));
		return this;
	}

	public static Order by(String key) {
		Order order = new Order();
		return order.and(key);
	}

	public Order descending() {
		orders.get(orders.size() - 1).setDescending(true);
		return this;
	}

	public Iterable<OrderItem> items() {
		return orders;
	}
}
