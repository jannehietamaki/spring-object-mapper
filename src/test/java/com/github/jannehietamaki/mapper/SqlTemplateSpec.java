package com.github.jannehietamaki.mapper;

import java.util.HashMap;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

import com.github.jannehietamaki.mapper.SqlTemplate;
import com.github.jannehietamaki.mapper.dialect.Dialect;
import com.github.jannehietamaki.mapper.dialect.HsqlDbDialect;

@RunWith(JDaveRunner.class)
public class SqlTemplateSpec extends Specification<SqlTemplate> {
	Dialect dialect = new HsqlDbDialect();

	public class WithUpdateTemplate {
		public SqlTemplate create() {
			return new SqlTemplate(dialect.update(), new HashMap<String, String>() {
				{
					put("table", "foobar");
					put("updates", "name=?, address=?");
					put("idField", "id");
				}
			});
		}

		public void finalSqlIsCreated() {
			specify(context.parse(), does.equal("UPDATE foobar SET name=?, address=? WHERE id=?"));
		}
	}
}
