package classLoader;

import org.junit.jupiter.api.Test;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class TCCL {

    @Test
    public void test() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        // jdk1.6 之后驱动支持spi，无需写这句
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println(Thread.currentThread().getContextClassLoader());
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            System.out.println(drivers.nextElement().getClass().getName());
        }
    }
}
