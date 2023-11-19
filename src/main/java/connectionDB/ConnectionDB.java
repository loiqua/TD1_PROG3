package connectionDB;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class ConnectionDB {
    @Bean
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                System.getenv("url"),
                System.getenv("username"),
                System.getenv("password"));
    }
}