package com.github.jannehietamaki.mapper.dialect;

public class HsqlDbDialect extends Dialect {
	public String getInsertedId() {
		return "CALL IDENTITY()";
	}
}
