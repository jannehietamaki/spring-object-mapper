/*
 * Copyright 2010 Janne Hietamaki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package springobjectmapper.dialect;

import java.util.List;

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

	public String selectBetween(String sql, List<Object> arguments, int first, int count) {
		arguments.add(first);
		arguments.add(count);
		return sql + " LIMIT ?,?";
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
