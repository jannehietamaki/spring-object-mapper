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

import static springobjectmapper.ReflectionHelper.*;
import static springobjectmapper.SqlStringUtils.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

public class TableProperties<T> {

    private final List<Field> allFields;
    private final List<Field> updateFields;
    private final Field idField;
    private final RowMapper<T> mapper;
    private final SqlTemplate sqlTemplate;

    public TableProperties(final Class<T> typeClass) {
        idField = getAnnotatedField(typeClass, Id.class);
        updateFields = getUpdateFields(typeClass);
        allFields = getUpdateFields(typeClass);
        allFields.add(idField); // idField is always the last one

        final List<String> fieldNames = getFieldNames(allFields);

        Map<String, String> tags = new HashMap<String, String>();
        tags.put("idField", fieldName(idField));
        tags.put("table", tableName(typeClass));
        tags.put("fields", StringUtils.arrayToDelimitedString(fieldNames.toArray(), ","));
        tags.put("values", placeHolders(fieldNames.size()));
        tags.put("updates", updates(updateFields));
        sqlTemplate = new SqlTemplate(tags);
        mapper = new FieldPropertyRowMapper<T>(typeClass, this);
    }

    private List<Field> getUpdateFields(final Class<T> typeClass) {
        List<Field> fields = getFields(typeClass);
        fields.remove(idField);
        return fields;
    }

    public String parse(String sql) {
        return sqlTemplate.parse(sql);
    }

    public RowMapper<T> mapper() {
        return mapper;
    }

    public Object[] valuesOf(T entity) {
        return getValues(allFields, entity);
    }

    public Object getId(T entity) {
        return getFieldValue(idField, entity);
    }

    public boolean isIdSet(T entity) {
        return getId(entity) != null;
    }

    public Field idField() {
        return idField;
    }

    public List<Field> allFields() {
        return allFields;
    }
}
