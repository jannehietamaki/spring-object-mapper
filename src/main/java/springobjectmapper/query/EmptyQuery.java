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

public class EmptyQuery implements IQuery {
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
