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
package springobjectmapper.repository;

import org.springframework.jdbc.core.simple.SimpleJdbcOperations;

import springobjectmapper.AbstractRepository;
import springobjectmapper.dialect.Dialect;
import springobjectmapper.model.Person;

public class PersonRepository extends AbstractRepository<Person> {

    public PersonRepository(SimpleJdbcOperations template, Dialect dialect) {
        super(template, dialect, Person.class);
    }
}
