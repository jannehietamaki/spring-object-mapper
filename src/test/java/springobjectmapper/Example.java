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

import java.util.List;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import springobjectmapper.dialect.Dialect;
import springobjectmapper.dialect.MySqlDialect;
import springobjectmapper.model.Country;
import springobjectmapper.model.Person;
import springobjectmapper.query.Order;
import springobjectmapper.query.Query;
import springobjectmapper.repository.CountryRepository;
import springobjectmapper.repository.PersonRepository;

public class Example {
    public static void main(String[] args) {
        // Dialect dialect = new HsqlDbDialect();
        // SingleConnectionDataSource dataSource = new
        // SingleConnectionDataSource("jdbc:hsqldb:mem:test", "sa", "", false);

        Dialect dialect = new MySqlDialect();
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/foo", "root", "", false);

        SimpleJdbcTemplate template = new SimpleJdbcTemplate(dataSource);
        
        template.update("CREATE TABLE country (id " + dialect.defaultIdFieldType() + " PRIMARY KEY, code CHAR(2) NOT NULL, name VARCHAR(50) NOT NULL)");
        template.update("CREATE TABLE person (id " + dialect.defaultIdFieldType() + " PRIMARY KEY,first_name VARCHAR(50) NOT NULL,last_name VARCHAR(50) NOT NULL,country_id BIGINT NOT NULL,email VARCHAR(50) NOT NULL,FOREIGN KEY (country_id) REFERENCES country(id))");

        PersonRepository persons = new PersonRepository(template, dialect);
        CountryRepository countries = new CountryRepository(template, dialect);

        Country uk = new Country("UK", "United Kingdom");
        countries.save(uk);

        Country ussr = new Country("SU", "Soviet Union");
        countries.save(ussr);

        persons.save(new Person("James", "Bond", "007@mi6.co.uk", uk));
        persons.save(new Person("Tatiana", "Romanova", "tatiana.romanova@kgb.su", ussr));
        persons.save(new Person("Alexander", "Trevelyan", "006@mi6.co.uk", uk));

        System.out.println("Good guys:");
        List<Person> results = persons.query(new Query("country_id=?", Order.by("last_name").descending(), uk.id()));
        for (Person person : results) {
            System.out.println(person.getFirstName() + " " + person.getLastName());
        }

        System.out.println("Bad guys:");
        results = persons.query(new Query("country_id!=?", Order.by("last_name").descending(), uk.id()));
        for (Person person : results) {
            System.out.println(person.getFirstName() + " " + person.getLastName());
        }
        dataSource.destroy();
    }
}
