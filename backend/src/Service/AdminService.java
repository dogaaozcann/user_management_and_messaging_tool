package backend.src.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import backend.src.Data.User;
import backend.src.Db.Database;

public class AdminService {
    
    private final Database db;
    private final UserService userService;

    public AdminService(Database db) {
        this.db = db;
        this.userService = new UserService(db);
    }


    //Admin Functions

    public String createUser(User u) throws SQLException {
        if (userService.registerUser(u) == null) {
            return "ERROR";
        }
        return "OK";
    }

    public List<User> viewUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        Connection c = db.getConnection();
        
        try (
            PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setIsAdmin(rs.getBoolean("is_admin"));
                users.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void searchUserByUsername(String username) throws SQLException {

        if (userService.searchUser(username)) {
            System.out.println("User found:");
            userService.printUser(userService.findUser(username));
        } else {
            System.out.println("User not found.");
        }
    }

    public void updateUser(User u, String attribute, String newValue) throws SQLException {
        String sql = "UPDATE users SET " + attribute + " = ? WHERE username = ?";
        Connection c = db.getConnection();
        try (
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newValue);
            ps.setString(2, u.getUsername());
            ps.executeUpdate();
                System.out.println("User updated successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
                System.out.println("User update failed. Please try again.");
        }
    }

    public boolean deleteUser(User u) throws SQLException {
        String sql1 = "UPDATE messages SET sender = NULL WHERE sender = ?";
        String sql2 = "UPDATE messages SET receiver = NULL WHERE receiver = ?";
        String sql  = "DELETE FROM users WHERE username = ?";
        Connection c = db.getConnection();
        try (
            PreparedStatement ps1 = c.prepareStatement(sql1);
            PreparedStatement ps2 = c.prepareStatement(sql2);
            PreparedStatement ps  = c.prepareStatement(sql)) {

        ps1.setString(1, u.getUsername());
        ps1.executeUpdate();
        ps2.setString(1, u.getUsername());
        ps2.executeUpdate();
        ps.setString(1, u.getUsername());
        int rows = ps.executeUpdate();
        return rows > 0;      // gerçekten silindi mi?

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setAdminStatus(User u, boolean isAdmin) throws SQLException {
        String sql = "UPDATE users SET is_admin = ? WHERE username = ?";
        Connection c = db.getConnection();
        try (
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setBoolean(1, isAdmin);
            ps.setString(2, u.getUsername());
            ps.executeUpdate();
            return true; 

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int countUsers() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM users";
        Connection c = db.getConnection();
        try (
            PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }

        } 
        
        catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }
}
