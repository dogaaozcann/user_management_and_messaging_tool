package backend.src.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
                birthdate DATE NOT NULL,
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

    public void seedDefaultAdmin() {
    String sql = "INSERT INTO users "
               + "(username, email, name, surname, birthdate, gender, address, password, is_admin) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, TRUE) "
               + "ON CONFLICT (username) DO NOTHING";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, "admin");
        ps.setString(2, "admin@admin.com");
        ps.setString(3, "Admin");
        ps.setString(4, "Adminoğlu");
        ps.setDate(5, java.sql.Date.valueOf("2001-01-01"));
        ps.setString(6, "Other");
        ps.setString(7, "Ankara");
        ps.setString(8, "Admin12345.");   

        ps.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public static void main(String[] args) {
        Database db = new Database();
        db.createTables();
    }
}


