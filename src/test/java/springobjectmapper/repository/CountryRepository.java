package springobjectmapper.repository;

import javax.sql.DataSource;

import springobjectmapper.AbstractRepository;
import springobjectmapper.dialect.Dialect;
import springobjectmapper.model.Country;


public class CountryRepository extends AbstractRepository<Country> {

	public CountryRepository(DataSource dataSource, Dialect dialect) {
		super(dataSource, dialect, Country.class);
	}

}
