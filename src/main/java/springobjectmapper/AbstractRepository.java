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

package springobjectmapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcOperations;
import org.springframework.util.ReflectionUtils;

import springobjectmapper.dialect.Dialect;
import springobjectmapper.query.IQuery;

public class AbstractRepository<T> {
    private final TableProperties<T> properties;
    private final SimpleJdbcOperations template;
    private final Dialect dialect;

    protected AbstractRepository() {
        // Default constructor is only used for creating proxies. Do not call directly
        this.properties = null;
        this.template = null;
        this.dialect = null;
    }

    public AbstractRepository(SimpleJdbcOperations template, Dialect dialect, final Class<T> typeClass) {
        this.dialect = dialect;
        this.template = template;
        properties = new TableProperties<T>(typeClass);
    }

    public T findById(Long id) {
        return findById(id, false);
    }

    public T findById(Long id, boolean returnNull) {
        if (id == null && returnNull) {
            return null;
        }
        return template.queryForObject(properties.parse(dialect.queryById()), rowMapper(), id);
    }

    public List<T> query(IQuery query) {
        return query(query, 0, 10000);
    }

    public List<T> query(IQuery query, int first, int count) {
        ArrayList<Object> args = new ArrayList<Object>(Arrays.asList(query.arguments()));
        String baseQuery = query.select(dialect);
        String orderedQuery = dialect.appendOrder(baseQuery, query.order());
        String betweenQuery = dialect.selectBetween(orderedQuery, args, first, count);
        return template.query(properties.parse(betweenQuery), rowMapper(), args.toArray());
    }

    public int count(IQuery query) {
        return template.queryForInt(properties.parse(query.count(dialect)), query.arguments());
    }

    public void save(T entity) {
        if (!properties.isIdSet(entity)) {
            insert(entity);
        } else {
            update(entity);
        }
    }

    public void remove(T entity) {
        if (template.update(properties.parse(dialect.remove()), properties.getId(entity)) != 1) {
            throw new RuntimeException("Remove failed!");
        }
    }

    public void update(final T entity) {
        if (template.update(properties.parse(dialect.update()), properties.valuesOf(entity)) != 1) {
            throw new RuntimeException("Update failed!");
        }
    }

    public void insert(final T entity) {
        if (dialect.supportsIdentityColumns()) {
            insertWithIdentity(entity);
        } else if (dialect.supportsSequenceColumns()) {
            insertWithSequence(entity);
        } else {
            throw new RuntimeException("Dialect does not support identity columns nor sequence columns!");
        }
    }

    private void insertWithSequence(T entity) {
        Object id = template.queryForObject(dialect.getNextSequenceValue(properties.getTableName() + "_SEQ"), properties.idField().getType());
        template.update(properties.parse(dialect.insertWithId()), properties.valuesOf(entity), id);
        ReflectionUtils.setField(properties.idField(), entity, id);
    }

    private void insertWithIdentity(T entity) {
        template.update(properties.parse(dialect.insert()), properties.valuesOf(entity));
        Object id = template.queryForObject(dialect.getInsertedId(), properties.idField().getType());
        ReflectionUtils.setField(properties.idField(), entity, id);
    }

    public RowMapper<T> rowMapper() {
        return properties.mapper();
    }
}
