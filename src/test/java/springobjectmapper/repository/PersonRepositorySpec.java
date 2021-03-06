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

import java.util.List;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.springframework.jdbc.core.simple.SimpleJdbcOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import springobjectmapper.dialect.Dialect;
import springobjectmapper.dialect.HsqlDbDialect;
import springobjectmapper.model.Country;
import springobjectmapper.model.Person;
import springobjectmapper.query.IQuery;
import springobjectmapper.query.Order;
import springobjectmapper.query.OrderedQuery;
import springobjectmapper.query.Query;

@RunWith(JDaveRunner.class)
public class PersonRepositorySpec extends Specification<PersonRepository> {
    private final Dialect dialect = new HsqlDbDialect();
    private Country country;
    private SimpleJdbcOperations template;
    private SingleConnectionDataSource dataSource;
    private int counter = 0;

    @Override
    public void create() {
        dataSource = new SingleConnectionDataSource("jdbc:hsqldb:mem:test" + (counter++), "sa", "", false);
        template = new SimpleJdbcTemplate(dataSource);
        createFixture();
        CountryRepository countryRepository = new CountryRepository(template, dialect);
        country = new Country("SE", "Sweden");
        countryRepository.save(country);
    }

    @Override
    public void destroy() throws Exception {
        template.update("DROP TABLE person");
        template.update("DROP TABLE country");
        dataSource.destroy();
    }

    private void createFixture() {
        template.update("CREATE TABLE country (id BIGINT IDENTITY PRIMARY KEY, code CHAR(2) NOT NULL, name VARCHAR(50) NOT NULL)");
        template.update("CREATE TABLE person (id BIGINT IDENTITY PRIMARY KEY,first_name VARCHAR(50) NOT NULL,last_name VARCHAR(50) NOT NULL,country_id BIGINT NOT NULL,email VARCHAR(50) NOT NULL,FOREIGN KEY (country_id) REFERENCES country(id))");
    }

    public class WithEmptyDatabase {
        public PersonRepository create() {
            return new PersonRepository(template, new HsqlDbDialect());
        }

        public void itemCanBeInsertedIntoDatabase() {
            Person person = new Person("James", "Bond", "james.bond@mi6.co.uk", country);
            context.save(person);
            specify(person.id(), isNotNull());
        }
    }

    public class WithDatabaseContainingRecords {
        public PersonRepository create() {
            PersonRepository repository = new PersonRepository(template, new HsqlDbDialect());
            for (int a = 0; a < 10; a++) {
                repository.save(new Person("James-" + a, "Bond", "james" + a + ".bond@mi6.co.uk", country));
            }
            return repository;
        }

        public void allItemsCanBeQueriedFromTheDatabase() {
            List<Person> persons = context.query(new OrderedQuery(Order.by("first_name")));
            specify(persons.size(), does.equal(10));
        }

        public void itemCanBeQueriedByIdAndUpdated() {
            List<Person> persons = context.query(IQuery.ALL);
            Long id = persons.get(0).id();
            Person first = context.findById(id);
            first.setEmail("foobar@zoo.com");
            context.save(first);
            first = context.findById(id);
            specify(first.getEmail(), does.equal("foobar@zoo.com"));
        }

        public void itemCanBeQueriedViaQueryInterface() {
            List<Person> persons = context.query(new Query("email=?", "james5.bond@mi6.co.uk"));
            specify(persons.size(), does.equal(1));
        }

        public void itemsCanBeCounted() {
            specify(context.count(new Query("email=?", "james5.bond@mi6.co.uk")), does.equal(1));
        }

        public void queryCanBeLimitedAndOrdered() {
            List<Person> persons = context.query(new OrderedQuery(Order.by("first_name")), 5, 2);
            specify(persons.size(), does.equal(2));
            specify(persons.get(0).getFirstName(), does.equal("James-5"));
            specify(persons.get(1).getFirstName(), does.equal("James-6"));
        }

        public void itemCanBeRemoved() {
            List<Person> persons = context.query(new Query("email=?", "james5.bond@mi6.co.uk"));
            context.remove(persons.get(0));
            specify(context.count(IQuery.ALL), does.equal(9));
        }
    }
}
