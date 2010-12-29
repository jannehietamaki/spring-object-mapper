package com.github.jannehietamaki.mapper.model;

import com.github.jannehietamaki.mapper.Id;

public class Country {
	@Id
	private Long id;
	private final String name;
	private final String code;

	public Country(String code, String name) {
		this.name = name;
		this.code = code;
	}

	public Long id() {
		return id;
	}
}
