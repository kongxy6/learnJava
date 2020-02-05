package database.mysql;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.*;

@Slf4j
public class BaseOperation {

    Connection connection =
            DriverManager.getConnection("jdbc:mysql://localhost:3306/test?allowMultiQueries=true", "root", "123456");

    public BaseOperation() throws SQLException {
    }

    @Test
    public void testStatement() throws SQLException {
        String sql = "select last_update_time from blog order by last_update_time";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        log.info("updateCount: {}", statement.getUpdateCount());
        while (resultSet.next()) {
            log.info("Time: {}", resultSet.getTimestamp(1));
        }
        assert !statement.getMoreResults() && -1 == statement.getUpdateCount();
    }

    @Test
    public void testStatement2() throws SQLException {
        String sql = "update blog set likes = 16 where id = 1";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        log.info("updateCount: {}", statement.getUpdateCount());
        assert !statement.getMoreResults() && -1 == statement.getUpdateCount();
    }

    @Test
    public void testPreparedStatement() throws SQLException {
        String sql = "select last_update_time from blog where id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, 1);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            log.info("Time: {}", resultSet.getTimestamp(1));
        }
        assert !statement.getMoreResults() && -1 == statement.getUpdateCount();
    }

    @Test
    public void testCallableStatement() throws SQLException {
        String sql = "select last_update_time from blog where id = ?;" +
                "update blog set likes = 16 where id = 1";
        CallableStatement statement = connection.prepareCall(sql);
        statement.setInt(1, 1);
        ResultSet resultSet = statement.executeQuery();
        log.info("updateCount: {}", statement.getUpdateCount());
        while (resultSet.next()) {
            log.info("Time: {}", resultSet.getTimestamp(1));
        }
        assert !statement.getMoreResults();
        // resultSet = statement.getResultSet();
        log.info("Count: {}", statement.getUpdateCount());
    }
}
