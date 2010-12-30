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

public class Query implements IQuery {
    private final String sql;
    private final Object[] args;
    private final Order order;

    public Query(String sql, Order order, Object... args) {
        this.sql = sql;
        this.args = args;
        this.order = order;
    }

    public Query(String sql, Object... args) {
        this.sql = sql;
        this.args = args;
        this.order = Order.ANY;
    }

    @Override
    public String select(Dialect dialect) {
        return dialect.select(sql);
    }

    @Override
    public String count(Dialect dialect) {
        return dialect.count(sql);
    }

    @Override
    public Object[] arguments() {
        return args;
    }

    @Override
    public Order order() {
        return order;
    }
}
