package com.github.jannehietamaki.mapper.repository;

import javax.sql.DataSource;

import com.github.jannehietamaki.mapper.AbstractRepository;
import com.github.jannehietamaki.mapper.dialect.Dialect;
import com.github.jannehietamaki.mapper.model.Country;

public class CountryRepository extends AbstractRepository<Country> {

	public CountryRepository(DataSource dataSource, Dialect dialect) {
		super(dataSource, dialect, Country.class);
	}

}
