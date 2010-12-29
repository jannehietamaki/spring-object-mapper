package springobjectmapper;

import java.util.HashMap;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

import springobjectmapper.SqlTemplate;
import springobjectmapper.dialect.Dialect;
import springobjectmapper.dialect.HsqlDbDialect;


@RunWith(JDaveRunner.class)
public class SqlTemplateSpec extends Specification<SqlTemplate> {
	Dialect dialect = new HsqlDbDialect();

	public class WithUpdateTemplate {
		public SqlTemplate create() {
			return new SqlTemplate(new HashMap<String, String>() {
				{
					put("table", "foobar");
					put("updates", "name=?, address=?");
					put("idField", "id");
				}
			});
		}

		public void finalSqlIsCreated() {
			specify(context.parse(dialect.update()), does.equal("UPDATE foobar SET name=?, address=? WHERE id=?"));
		}
	}
}
