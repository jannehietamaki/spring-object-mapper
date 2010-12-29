package com.github.jannehietamaki.mapper.repository;

import javax.sql.DataSource;

import com.github.jannehietamaki.mapper.AbstractRepository;
import com.github.jannehietamaki.mapper.dialect.Dialect;
import com.github.jannehietamaki.mapper.model.Person;

public class PersonRepository extends AbstractRepository<Person> {

	public PersonRepository(DataSource dataSource, Dialect dialect) {
		super(dataSource, dialect, Person.class);
	}
}
