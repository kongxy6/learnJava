package mybatis.typeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(value = JdbcType.VARCHAR)
@MappedTypes(String.class)
public class UpperCaseTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        System.out.println("===== UpperCaseTypeHandler set =====");
        ps.setString(i, parameter.toUpperCase());
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        System.out.println("===== UpperCaseTypeHandler get =====");
        return rs.getString(columnName).toUpperCase();
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        System.out.println("===== UpperCaseTypeHandler get =====");
        return rs.getString(columnIndex).toUpperCase();
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        System.out.println("===== UpperCaseTypeHandler get =====");
        return cs.getString(columnIndex).toUpperCase();
    }
}