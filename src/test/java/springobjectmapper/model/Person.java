package springobjectmapper.model;

import springobjectmapper.Id;

public class Person {
	@Id
	private Long id;
	private final String firstName;
	private final String lastName;
	private String email;
	private final Long countryId;

	public Person(String firstName, String lastName, String email, Country country) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.countryId = country.id();
	}

	public Long id() {
		return id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
}
