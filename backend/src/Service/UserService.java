package backend.src.Service;

import backend.src.Data.User;
import backend.src.Db.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    private final Database db;

    public UserService(Database db) {
        this.db = db;
    }


    //Core Function

    public User findUser(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        
        try (Connection c = db.getConnection();
            PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setBirthdate(rs.getDate("birthdate").toString());
                user.setGender(rs.getString("gender"));
                user.setAddress(rs.getString("address"));
                user.setPassword(rs.getString("password"));
                user.setIsAdmin(rs.getBoolean("is_admin"));
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    //User Control Functions

    public boolean searchUser(String username) {
        return findUser(username) != null;
    }

    public boolean searchEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        
        try (Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean controlPassword(User currentUser, String password) {
        return currentUser.getPassword().equals(password);
    }

    public User loginUser(String username, String enteredPassword) {
        User currentUser = findUser(username);
       
        if(currentUser == null) {
            System.out.println("User not found.");
            return null;
        }

        if (controlPassword(currentUser, enteredPassword) == true) {
            System.out.println("Login successful.");
            return currentUser;
        } else {
            System.out.println("Incorrect password. Please try again.");
            return null;
        }
    }
    
    public User registerUser(User u) {

        // Add user to the database:
        String sql = "INSERT INTO users (username, email, name, surname, birthdate, gender, address, password, is_admin) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            
            ps.setString(1, u.getUsername());
            if (searchUser(u.getUsername())) {
                System.out.println("Username already exists. Please choose a different username.");
                return null;
            }
            ps.setString(2, u.getEmail());
            
            if (searchEmail(u.getEmail())) {
                System.out.println("Email already exists. Please choose a different email.");
                return null;
            }
            ps.setString(3, u.getName());
            ps.setString(4, u.getSurname());

            ps.setDate(5, java.sql.Date.valueOf(u.getBirthdate()));

            ps.setString(6, u.getGender());
            if (!u.getGender().equalsIgnoreCase("F") && !u.getGender().equalsIgnoreCase("M") && !u.getGender().equalsIgnoreCase("Other")) {
                System.out.println("Invalid gender input.");
                return null;
            }

            ps.setString(7, u.getAddress());
            ps.setString(8, u.getPassword());
            ps.setBoolean(9, false); // Default to non-admin user.

            ps.executeUpdate();
            return u; // Return the user object if registration is successful.

        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        
        return null;
    }

    public void printUser(User u) {
        System.out.println("\nUsername: " + u.getUsername());
        System.out.println("Email: " + u.getEmail());
        System.out.println("Name: " + u.getName());
        System.out.println("Surname: " + u.getSurname());
        System.out.println("Birthdate: " + u.getBirthdate());
        System.out.println("Gender: " + u.getGender());
        System.out.println("Address: " + u.getAddress());   
    } 
}


