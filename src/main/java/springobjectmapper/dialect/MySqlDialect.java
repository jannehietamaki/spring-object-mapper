package springobjectmapper.dialect;

import java.sql.SQLException;

import com.mysql.jdbc.Driver;

public class MySqlDialect extends Dialect {
    public MySqlDialect() {
        try {
            new Driver();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public String getInsertedId() {
        return "SELECT LAST_INSERT_ID()";
    }

    @Override
    public String defaultIdFieldType() {
        return "BIGINT AUTO_INCREMENT";
    }

}
