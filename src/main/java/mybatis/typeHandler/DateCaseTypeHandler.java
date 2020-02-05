package mybatis.typeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@MappedJdbcTypes(value = JdbcType.VARCHAR)
@MappedTypes(Date.class)
public class DateCaseTypeHandler extends BaseTypeHandler<Date> {

    private String pattern = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Date date, JdbcType jdbcType) throws SQLException {
        System.out.println("===== DateCaseTypeHandler set =====");
        //preparedStatement.setTimestamp(i, new java.sql.Timestamp(date.getTime()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        preparedStatement.setString(i, simpleDateFormat.format(date));
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, String s) throws SQLException {
        System.out.println("===== DateCaseTypeHandler get =====");
        return new Date(resultSet.getTimestamp(s).getTime());
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, int i) throws SQLException {
        System.out.println("===== DateCaseTypeHandler get =====");
        // 即使数据库字段为VARCHAR也可以getTimestamp取出正确的值
        // 将指定列值解析为java.sql.Timestamp
        return new Date(resultSet.getTimestamp(i).getTime());
    }

    @Override
    public Date getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        System.out.println("===== DateCaseTypeHandler get =====");
        return new Date(callableStatement.getTimestamp(i).getTime());
    }
}
