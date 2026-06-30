package backend.src.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import backend.src.Data.Message;
import backend.src.Db.Database;

public class MessageService {

    private final Database db;

    public MessageService(Database db) {
        this.db = db;
    }
    
    public void sendMessage(Message m) {
        String sql = "INSERT INTO messages (sender, receiver, subject, content) VALUES (?, ?, ?, ?)";
        
        try (Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getSender());
            ps.setString(2, m.getReceiver());
            ps.setString(3, m.getSubject());
            ps.setString(4, m.getContent());
            ps.executeUpdate();
                System.out.println("Message sent successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
                System.out.println("Message sending failed. Please try again.");
        }

    }
    
    public void viewInMessages(String username, int page) {       
        String sql = "SELECT * FROM messages WHERE receiver = ? ORDER BY senttime DESC LIMIT 10 OFFSET ?";

        try (Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, (page - 1) * 10);
            ResultSet rs = ps.executeQuery();
            
            System.out.println("INBOX");
            System.out.println("--------------------------------------------------\n");
            System.out.println("Page " + page + "\n");

                for (int i = (page - 1) * 10; rs.next(); i++) {
                    System.out.println((i + 1) + "." + "Sender: " + rs.getString("sender") 
                    + ", Subject: " + rs.getString("subject") + ", " 
                    + rs.getString("senttime") + ", " + (rs.getBoolean("is_read") ? " " : "UNREAD"));
                }

        } catch (SQLException e) {
            e.printStackTrace();
                System.out.println("The inbox could not load. Please try again.");
        }
    }  

    public void viewOutMessages(String username, int page) {
        String sql = "SELECT * FROM messages WHERE sender = ? ORDER BY senttime DESC LIMIT 10 OFFSET ?";

        try (Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, (page - 1) * 10);
            ResultSet rs = ps.executeQuery();

            System.out.println("OUTBOX");
            System.out.println("--------------------------------------------------\n");
                for (int i = (page - 1) * 10; rs.next(); i++) {
                    System.out.println((i + 1) + "." + "Receiver: " + rs.getString("receiver") 
                    + ", Subject: " + rs.getString("subject") + ", " 
                    + rs.getString("senttime"));
                }

        } catch (SQLException e) {
            e.printStackTrace();
                System.out.println("The outbox could not load. Please try again.");
        }

        

    }

    public void printMessage(Message m) {
        System.out.println("Sender: " + m.getSender());
        System.out.println("Receiver: " + m.getReceiver());
        System.out.println("Timestamp: " + m.getTimestamp());
        System.out.println();
        System.out.println("Subject: " + m.getSubject());
        System.out.println("Content: " + m.getContent());
        System.out.println("Is Read: " + m.isRead());
    }

    public void markAsRead(Message m) {
        m.setIsRead(true);
    }

}
