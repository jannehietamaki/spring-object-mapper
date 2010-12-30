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
