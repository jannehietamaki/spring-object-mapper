package com.github.jannehietamaki.mapper.dialect;

public abstract class Dialect {

	public String queryById() {
		return "SELECT * FROM {table} WHERE {idField}=?";
	}

	public abstract String getInsertedId();
	
	public String insert() {
		return "INSERT INTO {table} ({fields}) VALUES ({values})";
	}

	public String update() {
		return "UPDATE {table} SET {updates} WHERE {idField}=?";
	}
}
