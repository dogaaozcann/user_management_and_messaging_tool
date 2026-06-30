package backend.src.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/ummt";
    private static final String USER = "postgres";
    private static final String PASS = "Do12345.";
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public void createTables() {
            String userSql = """
            CREATE TABLE IF NOT EXISTS users (
                username VARCHAR(12) PRIMARY KEY,
                email VARCHAR(50) UNIQUE NOT NULL,
                name VARCHAR(20) NOT NULL,
                surname VARCHAR(20) NOT NULL,
                birthdate DATE,
                gender VARCHAR(10),
                address TEXT,
                password VARCHAR(50) NOT NULL,
                is_admin BOOLEAN DEFAULT FALSE
            )
             """;

            String messageSql = """
            CREATE TABLE IF NOT EXISTS messages (
                id SERIAL PRIMARY KEY,
                sender VARCHAR(12) REFERENCES users(username),
                receiver VARCHAR(12) REFERENCES users(username),
                senttime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                subject VARCHAR(50),
                content TEXT,
                is_read BOOLEAN DEFAULT FALSE
            )
             """;

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(userSql);
            stmt.executeUpdate(messageSql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Database db = new Database();
        db.createTables();
    }
}


