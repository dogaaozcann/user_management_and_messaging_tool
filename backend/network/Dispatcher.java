package backend.network;

import java.util.List;

import backend.src.Data.Message;
import backend.src.Data.Session;
import backend.src.Data.User;
import backend.src.Db.Database;
import backend.src.Service.AdminService;
import backend.src.Service.MessageService;
import backend.src.Service.UserService;

public class Dispatcher {

    private final UserService userService;
    private final MessageService messageService;
    private final AdminService adminService;

    public Dispatcher(Database db) {
        this.adminService = new AdminService(db);
        this.userService = new UserService(db);
        this.messageService = new MessageService(db);
    }

    public String handleRequest(Session session, String request) {
        String[] p = request.split("\\|\\|\\|", -1);
        String action = p[0];

        switch (action) {

            //User Actions

            case "LOGIN": {
                User u = userService.loginUser(p[1], p[2]);
                    if(u == null)
                        return "ERROR";
                session.setCurrentUser(u);
                return "OK|||" + u.getUsername() + "|||" + u.isAdmin();
            }

            case "REGISTER": {
                User u = new User();
                u.setUsername(p[1]);
                u.setEmail(p[2]);
                u.setName(p[3]);
                u.setSurname(p[4]);
                u.setBirthdate(p[5]);
                u.setGender(p[6]);
                u.setAddress(p[7]);
                u.setPassword(p[8]);
                u.setIsAdmin(false);

                userService.registerUser(u);

                if (userService.searchUser(u.getUsername())) {
                    return "OK|||" + u.getUsername() + "|||" + u.getEmail() 
                    + "|||" + u.getName() + "|||" + u.getSurname() + "|||" + u.getBirthdate() + "|||" + u.getGender() + "|||" + u.getAddress();
                } else {
                    return "ERROR";
                }   
            } 
        
            case "LOGOUT": {
                session.setCurrentUser(null);
                return "OK";
            }

            //Admin Actions
            
            case "ADDUSER": {

                if (!session.isLoggedIn() || !session.getCurrentUser().isAdmin()) {
                    return "ERROR|||Not authorized.";
                }
                User u = new User();
                u.setUsername(p[1]);
                u.setEmail(p[2]);
                u.setName(p[3]);
                u.setSurname(p[4]);
                u.setBirthdate(p[5]);
                u.setGender(p[6]);
                u.setAddress(p[7]);
                u.setPassword(p[8]);
                u.setIsAdmin(false);

                adminService.createUser(u);

                if (userService.searchUser(u.getUsername())) {
                    return "OK|||";
                } else {
                    return "ERROR";
                }   
            }
            case "UPDATEUSER": {
                if (!session.isLoggedIn() || !session.getCurrentUser().isAdmin()) {
                    return "ERROR|||Not authorized.";
                }
                User u = userService.findUser(p[1]);
                if (u == null) {
                    return "ERROR";
                }
                String attribute = p[2];
                String newValue = p[3];

                adminService.updateUser(u, attribute, newValue);
                return "OK";
            }
            case "DELETEUSER": {
                if (!session.isLoggedIn() || !session.getCurrentUser().isAdmin()) {
                    return "ERROR|||Not authorized.";
                }
                User u = userService.findUser(p[1]);
                if (u == null) {
                    return "ERROR";
                }
                adminService.deleteUser(u);
                return "OK";
            }
        
            case "VIEWUSERS": {
                if (!session.isLoggedIn() || !session.getCurrentUser().isAdmin()) {
                    return "ERROR|||Not authorized.";
                }
                List<User> users = adminService.viewUsers();
                StringBuilder sb = new StringBuilder("OK|||");
                for (User u : users) {
                    sb.append("###");
                    sb.append(u.getUsername()).append("|||")
                            .append(u.getEmail()).append("|||")
                            .append(u.isAdmin()).append("|||");
                }
                return sb.toString();
            }
            case "USERDETAILS": {
                if (!session.isLoggedIn()|| !session.getCurrentUser().isAdmin()) {
                    return "ERROR|||Not authorized.";
                }
                User u = userService.findUser(p[1]);
                if (u == null) {
                    return "ERROR";
                }
                return "OK|||"+u.getUsername()+"|||"+u.getEmail()+"|||"+u.getName()+"|||"+u.getSurname()+"|||"+u.getBirthdate()+"|||"+u.getGender()+"|||"+u.getAddress();
            }
            case "SEARCHUSER": {
                if (!session.isLoggedIn()|| !session.getCurrentUser().isAdmin()) {
                    return "ERROR|||Not authorized.";
                }
                User u = userService.findUser(p[1]);
                if (u == null) {
                    return "ERROR";
                }
                return "OK|||"+u.getUsername()+"|||"+u.getEmail()+"|||"+u.getName()+"|||"+u.getSurname()+"|||"+u.getBirthdate()+"|||"+u.getGender()+"|||"+u.getAddress();
            }

            //Message Actions

            case "SENDMSG": {
                if (!session.isLoggedIn()) {
                    return "ERROR|||Not logged in.";
                }
                Message m = new Message();
                m.setSender(session.getCurrentUser().getUsername());
                m.setReceiver(p[1]);
                m.setSubject(p[2]);
                m.setContent(p[3]);

                messageService.sendMessage(m);
                return "OK";
            }

            case "READMSG": {
                if (!session.isLoggedIn()) {
                    return "ERROR|||Not logged in.";
                }
                String username = session.getCurrentUser().getUsername(); //People can only read their own messages.
                Message m = messageService.readMessage(username, Integer.parseInt(p[1]));
                if (m == null) {
                    return "ERROR|||Message not found.";
                }
                return "OK|||" + m.getId() + "|||" + m.getSender() + "|||" + m.getSubject() + "|||" + m.getContent() + "|||" + m.getTimestamp();
            }  
            
            case "INBOX": {
                if (!session.isLoggedIn()) {
                    return "ERROR|||Not logged in.";
                }

                String username = session.getCurrentUser().getUsername();//People can only view their own inbox.

                List<Message> messages = messageService.InMessages(username, Integer.parseInt(p[1]));
                StringBuilder sb = new StringBuilder("OK|||");
                for (Message m : messages) {
                    sb.append("###");
                    sb.append(m.getId()).append("|||");
                    sb.append(m.getSender()).append("|||")
                            .append(m.getSubject()).append("|||")
                            .append(m.getTimestamp()).append("|||")
                            .append(m.isRead()).append("|||");
                }
                return sb.toString();
            }
            case "OUTBOX": {
                if (!session.isLoggedIn()) {
                    return "ERROR|||Not logged in";
                }
                
                String username = session.getCurrentUser().getUsername();//People can only view their own outbox.

                List<Message> messages = messageService.OutMessages(username, Integer.parseInt(p[1]));
                StringBuilder sb = new StringBuilder("OK|||");
                for (Message m : messages) {
                    sb.append("###");
                    sb.append(m.getId()).append("|||");
                    sb.append(m.getReceiver()).append("|||")
                            .append(m.getSubject()).append("|||")
                            .append(m.getTimestamp()).append("|||");
                }
                return sb.toString();
            }
            
            default:
                return "ERROR|||Unknown action: " + action;
        }
    
    }}
