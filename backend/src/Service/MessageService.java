package backend.src.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import backend.src.Data.Message;
import backend.src.Db.Database;

public class MessageService {

    private final Database db;

    public MessageService(Database db) {
        this.db = db;
    }
    
    //Core Function
    
    public Message selectMessage(String username, int messageId) {
        String sql = "SELECT * FROM messages WHERE id = ? AND (sender = ? OR receiver = ?)";
        try (Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, messageId);
            ps.setString(2, username);
            ps.setString(3, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Message m = new Message();
                m.setId(rs.getInt("id"));
                m.setSender(rs.getString("sender"));
                m.setReceiver(rs.getString("receiver"));
                m.setSubject(rs.getString("subject"));
                m.setContent(rs.getString("content"));
                m.setTimestamp(rs.getString("senttime"));
                m.setIsRead(rs.getBoolean("is_read"));
                return m;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
    
    public List<Message> InMessages(String username, int page) {
    List<Message> messages = new ArrayList<>();
    String sql = "SELECT * FROM messages WHERE receiver = ? ORDER BY senttime DESC LIMIT 10 OFFSET ?";

    try (Connection c = db.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)) {
        ps.setString(1, username);
        ps.setInt(2, (page - 1) * 10);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Message m = new Message();
            m.setId(rs.getInt("id"));
            m.setSender(rs.getString("sender"));
            m.setReceiver(rs.getString("receiver"));
            m.setSubject(rs.getString("subject"));
            m.setContent(rs.getString("content"));
            m.setTimestamp(rs.getString("senttime"));
            m.setIsRead(rs.getBoolean("is_read"));
            messages.add(m);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return messages;
}

    public List<Message> OutMessages(String username, int page) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE sender = ? ORDER BY senttime DESC LIMIT 10 OFFSET ?";

        try (Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, (page - 1) * 10);
            ResultSet rs = ps.executeQuery();

        while (rs.next()) {
                Message m = new Message();
                m.setSender(rs.getString("sender"));
                m.setReceiver(rs.getString("receiver"));
                m.setSubject(rs.getString("subject"));
                m.setContent(rs.getString("content"));
                m.setTimestamp(rs.getString("senttime"));
                m.setId(rs.getInt("id"));
                messages.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
                System.out.println("The outbox could not load. Please try again.");
        }

        return messages;

    }

    public Message readMessage(String username, int messageId) {
        Message m = selectMessage(username, messageId);

        try (Connection c = db.getConnection()) {       
            markAsRead(m);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to mark the message as read. Please try again.");
        }
  
        try (Connection c = db.getConnection()) {
            printMessage(m);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve the message. Please try again.");
            }
        return m;
        }
    
    
    public void printMessage(Message m) {
        System.out.println("Sender: " + m.getSender());
        System.out.println("Receiver: " + m.getReceiver());
        System.out.println("Timestamp: " + m.getTimestamp());
        System.out.println("Subject: " + m.getSubject());
        System.out.println("Content: " + m.getContent());;
    }

    public void markAsRead(Message m) {
        String sql = "UPDATE messages SET is_read = TRUE WHERE id = ?";
        try (Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, m.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
                System.out.println("Failed to mark the message as read. Please try again.");
        }
    }

    public void deleteMessage(Message m) {
        String sql = "DELETE FROM messages WHERE id = ?";
        try (Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, m.getId());
            ps.executeUpdate();
                System.out.println("Message deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
                System.out.println("Failed to delete the message. Please try again.");
        }
    }

    public int countMessages(String username) {
        String sql = "SELECT COUNT(*) AS total FROM messages WHERE receiver = ? OR sender = ?";
        try (Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }
}