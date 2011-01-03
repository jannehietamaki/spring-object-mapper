package springobjectmapper.dialect;

public class OracleDialect extends Dialect {

    @Override
    public String defaultIdFieldType() {
        return "NUMBER";
    }

    @Override
    public boolean supportsSequenceColumns() {
        return true;
    }
}
