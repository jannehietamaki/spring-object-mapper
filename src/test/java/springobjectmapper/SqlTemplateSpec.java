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

import java.util.HashMap;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

import springobjectmapper.SqlTemplate;
import springobjectmapper.dialect.Dialect;
import springobjectmapper.dialect.HsqlDbDialect;

@RunWith(JDaveRunner.class)
public class SqlTemplateSpec extends Specification<SqlTemplate> {
    Dialect dialect = new HsqlDbDialect();

    public class WithUpdateTemplate {
        public SqlTemplate create() {
            return new SqlTemplate(new HashMap<String, String>() {
                {
                    put("table", "foobar");
                    put("updates", "name=?, address=?");
                    put("idField", "id");
                }
            });
        }

        public void finalSqlIsCreated() {
            specify(context.parse(dialect.update()), does.equal("UPDATE foobar SET name=?, address=? WHERE id=?"));
        }
    }
}
