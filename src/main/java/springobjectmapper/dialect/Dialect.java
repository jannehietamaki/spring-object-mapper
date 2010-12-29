package springobjectmapper.dialect;

import springobjectmapper.query.Order;
import springobjectmapper.query.OrderItem;

public abstract class Dialect {

	public String queryById() {
		return "SELECT * FROM {table} WHERE {idField}=?";
	}

	public abstract String getInsertedId();

	public String insert() {
		return "INSERT INTO {table} ({fields}) VALUES ({values})";
	}

	public String update() {
		return "UPDATE {table} SET {updates} WHERE {idField}=?";
	}

	public String selectBetween(String sql, int first, int count) {
		return sql + " LIMIT " + first + "," + count;
	}

	public String select(String sql) {
		if (sql.isEmpty()) {
			return "SELECT * FROM {table}";
		}
		return "SELECT * FROM {table} WHERE " + sql;
	}

	public String count(String sql) {
		if (sql.isEmpty()) {
			return "SELECT COUNT(*) FROM {table}";
		}
		return "SELECT COUNT(*) FROM {table} WHERE " + sql;
	}

	public String orderBy(Order orders) {
		StringBuilder order = new StringBuilder();
		Iterable<OrderItem> items = orders.items();
		if (items.iterator().hasNext()) {
			order.append(" ORDER BY ");
			boolean first = true;
			for (OrderItem item : items) {
				if (!first) {
					order.append(",");
				}
				first = false;
				order.append(item.key());
				if (item.descending()) {
					order.append(" DESC");
				}
			}
		}
		return order.toString();
	}

	public String appendOrder(String sql, Order order) {
		return sql + orderBy(order);
	}
}
