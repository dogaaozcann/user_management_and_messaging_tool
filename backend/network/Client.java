package backend.network;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import frontend.MainMenu;
import frontend.MainMenuAdmin;
import frontend.MainMenuUser;
import frontend.Box.BoxPage.Inbox;
import frontend.Box.BoxPage.Outbox;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8000);
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8)); 
        BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));
        PrintWriter    out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        MainMenu m = new MainMenu();
        while(true) {
            String request = m.mainMenu(scanner);
            if (request.equals("EXIT")) {
                continue;
            }
            if(request.equals("SHUTDOWN")) {
                break;
            }
            if(request.isEmpty()) {
                continue;
            }

            out.println(request);

            String response = in.readLine();

            if(request.startsWith("REGISTER") && response.startsWith("OK")) {
                System.out.println("\nYou have successfully registered. Please log in.");
            } else if(request.startsWith("REGISTER") && response.startsWith("ERROR")) {
                System.out.println("\nRegistration failed. Please try again.");
            } else if(request.startsWith("LOGIN") && response.startsWith("ERROR")) {
                System.out.println("\nLogin failed. Please try again.");
            } else
            
                if (request.startsWith("LOGIN") && response.startsWith("OK")) {
                    System.out.println("\nYou have successfully logged in.");
                    String[] parts = response.split("\\|\\|\\|");
                    boolean isAdmin = Boolean.parseBoolean(parts[2]);
                
                while (true) {
                        MainMenuUser userMenu = new MainMenuUser();
                        String userRequest = userMenu.mainMenuUser(scanner, isAdmin);

                        if(userRequest.equals("INBOX")) {
                            new Inbox().inbox(scanner, out, in);
                            continue; 
                        }
                        if(userRequest.equals("OUTBOX")) {
                            new Outbox().outbox(scanner, out, in);
                            continue; 
                        }   
                        
                        if (userRequest.equals("LOGOUT")) {
                            out.println("LOGOUT");
                            in.readLine();
                            break;
                        }
                        if (userRequest.equals("ADMINMENU")) {
                            new MainMenuAdmin().mainMenuAdmin(scanner, out, in);
                            continue;
                        }

                        out.println(userRequest);
                        String userResponse = in.readLine();

                        if (userRequest.startsWith("SENDMSG")) {
                            if (userResponse.startsWith("OK")) {
                                System.out.println("\nMessage sent successfully.");
                            } else 
                                System.out.println("\nMessage sending failed. Please try again.");
                            }
                    }
            }
        }
        socket.close();
        scanner.close();
    }
}
