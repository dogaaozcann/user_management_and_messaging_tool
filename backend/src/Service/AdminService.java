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

    public void createUser(User u) {
        userService.registerUser(u);
    }

    public List<User> viewUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        
        try (Connection c = db.getConnection();
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

    public void searchUserByUsername(String username) {

        if (userService.searchUser(username)) {
            System.out.println("User found:");
            userService.printUser(userService.findUser(username));
        } else {
            System.out.println("User not found.");
        }
    }

    public void updateUser(User u, String attribute, String newValue) {
        String sql = "UPDATE users SET " + attribute + " = ? WHERE username = ?";
        
        try (Connection c = db.getConnection();
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

    public void deleteUser(User u) {
        String sql = "DELETE FROM users WHERE username = ?";
        
        try (Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.executeUpdate();
                System.out.println("User deleted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
                System.out.println("User deletion failed. Please try again.");
        }
    }

    public boolean setAdminStatus(User u, boolean isAdmin) {
        String sql = "UPDATE users SET is_admin = ? WHERE username = ?";
        
        try (Connection c = db.getConnection();
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

    public int countUsers() {
        String sql = "SELECT COUNT(*) AS total FROM users";
        
        try (Connection c = db.getConnection();
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
