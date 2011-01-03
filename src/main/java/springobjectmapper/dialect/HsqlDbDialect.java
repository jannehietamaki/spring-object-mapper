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

import org.hsqldb.jdbcDriver;

public class HsqlDbDialect extends Dialect {
    private static final String SELECT = "SELECT ";

    public HsqlDbDialect() {
        new jdbcDriver();
    }

    @Override
    public String defaultIdFieldType() {
        return "BIGINT IDENTITY";
    }

    @Override
    public String getInsertedId() {
        return "CALL IDENTITY()";
    }

    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public String selectBetween(String sql, List<Object> arguments, int first, int count) {
        if (sql.startsWith(SELECT)) {
            arguments.add(0, count);
            arguments.add(0, first);
            return "SELECT LIMIT ? ? " + sql.substring(SELECT.length());
        }
        throw new IllegalArgumentException("Invalid query for HSQLDB LIMIT: " + sql);
    }
}
