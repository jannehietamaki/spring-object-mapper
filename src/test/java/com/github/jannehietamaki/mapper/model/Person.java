package com.github.jannehietamaki.mapper.model;

import com.github.jannehietamaki.mapper.AbstractModel;

public class Person extends AbstractModel {
	private final String firstName;
	private final String lastName;
	private String email;
	private final Long countryId;

	@Override
	public String tableName() {
		return "person";
	}

	public Person(String firstName, String lastName, String email, Country country) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.countryId = country.id();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
