package springobjectmapper.dialect;

public class HsqlDbDialect extends Dialect {
	private static final String SELECT = "SELECT ";

	public String getInsertedId() {
		return "CALL IDENTITY()";
	}

	public String selectBetween(String sql, int first, int count) {
		if (sql.startsWith(SELECT)) {
			return "SELECT LIMIT " + first + " " + count + " " + sql.substring(SELECT.length());
		}
		throw new IllegalArgumentException("Invalid query for HSQLDB LIMIT: " + sql);
	}
}
