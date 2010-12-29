package springobjectmapper.dialect;

public class HsqlDbDialect extends Dialect {
	public String getInsertedId() {
		return "CALL IDENTITY()";
	}
}
