package com.github.jannehietamaki.mapper.model;

import com.github.jannehietamaki.mapper.AbstractModel;

public class Country extends AbstractModel {
	private final String name;
	private final String code;

	public Country(String code, String name) {
		this.name = name;
		this.code = code;
	}

	@Override
	public String tableName() {
		return "country";
	}

}
