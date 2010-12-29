package com.github.jannehietamaki.mapper;

public abstract class AbstractModel {
	private Long id;

	public Long id() {
		return id;
	}

	public abstract String tableName();
}
