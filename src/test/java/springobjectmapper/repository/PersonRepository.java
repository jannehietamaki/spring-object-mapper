package springobjectmapper.repository;

import javax.sql.DataSource;

import springobjectmapper.AbstractRepository;
import springobjectmapper.dialect.Dialect;
import springobjectmapper.model.Person;


public class PersonRepository extends AbstractRepository<Person> {

	public PersonRepository(DataSource dataSource, Dialect dialect) {
		super(dataSource, dialect, Person.class);
	}
}
