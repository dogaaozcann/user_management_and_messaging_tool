package frontend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import frontend.Admin.ViewUsers;

public class MainMenuAdmin {
    public void mainMenuAdmin(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        while(true) {
            System.out.println("\nWelcome to the Admin Main Menu.");
            System.out.println("==============================================");
            System.out.println("Please select an option:");
            System.out.println("1. Create User");
            System.out.println("2. Update User");
            System.out.println("3. Delete User");
            System.out.println("4. View Users");
            System.out.println("5. Search User");
            System.out.println("6. Set Admin Status");

            System.out.println("7. Go back to the User Main Menu.");

            String c = scanner.nextLine();

            switch (c) {
                case "1":
                    System.out.println("\nEnter the username of the new user:");
                    String usernamec = scanner.nextLine();
                    System.out.println("Enter the email of the new user:");
                    String email = scanner.nextLine();
                    System.out.println("Enter the name of the new user:");
                    String name = scanner.nextLine();
                    System.out.println("Enter the surname of the new user:");
                    String surname = scanner.nextLine();
                    System.out.println("Enter the birthdate of the new user:");
                    String birthdate = scanner.nextLine();
                    System.out.println("Enter the gender of the new user:");
                    String gender = scanner.nextLine();
                    System.out.println("Enter the address of the new user:");
                    String address = scanner.nextLine();
                    System.out.println("Enter the password of the new user:");
                    String password = scanner.nextLine();

                    out.println("ADDUSER|||" + usernamec + "|||" + email + "|||" + name + "|||" + surname + "|||" + birthdate + "|||" + gender + "|||" + address + "|||" + password);
                    String cu = in.readLine();

                    if(cu.startsWith("OK")) {
                        System.out.println("\nUser created successfully.");
                    } else {
                        System.out.println("\nFailed to create user. Please try again later.");
                    }
                    break;
                    
                    case "2": 
                    System.out.println("\nEnter the username of the user you want to update:");
                    String usernameu = scanner.nextLine();
                    System.out.println("Enter the attribute you want to update (email, name, surname, birthdate, gender, address, password):");
                    String attribute = scanner.nextLine();  
                    System.out.println("Enter the new value for the attribute:");
                    String newValue = scanner.nextLine();
                    
                    out.println("UPDATEUSER|||" + usernameu + "|||" + attribute + "|||" + newValue);
                    String ru = in.readLine();
                    if(ru.startsWith("OK")) {
                        System.out.println("\nUser updated successfully.");
                    } else {
                        System.out.println("\nFailed to update user. Please try again later.");
                    }
                    break;
                    
                    case "3":
                    System.out.println("\nEnter the username of the user you want to delete:");
                    String usernamed = scanner.nextLine();
                    out.println("DELETEUSER|||" + usernamed);
                    String rd = in.readLine();
                    if(rd.startsWith("OK")) {
                        System.out.println("\nUser deleted successfully.");
                        out.println("DELETELOGOUT|||" + usernamed);
                    } else {
                        System.out.println("\nFailed to delete user. Please try again later.");
                    }
                    break;
                
                    case "4":
                    out.println("VIEWUSERS");
                    String rv = in.readLine();
                    new ViewUsers().viewUsers(rv, out, in, scanner);
                    break;
                
                case "5":
                    System.out.println("\nEnter the username of the user you want to search:");
                    String usernames = scanner.nextLine();

                    out.println("SEARCHUSER|||" + usernames);
                    String rs = in.readLine();

                    if(rs.startsWith("OK")) {
                        String[] r = rs.split("\\|\\|\\|");
                        System.out.println("\nUser found:");
                        System.out.println("Username: " + r[1]);
                        System.out.println("Email: " + r[2]);
                        System.out.println("Admin Status: " + r[8]);
                    } else {
                        System.out.println("\nUser not found.");
                    }
                    break;
                
                case "6":
                    System.out.println("\nEnter the username of the user you want to set admin status for:");
                    String usernamea = scanner.nextLine();
                    System.out.println("Enter the new admin status (true/false):");
                    String admin = scanner.nextLine();

                    out.println("SETADMIN|||" + usernamea + "|||" + admin);
                    String ra = in.readLine();

                    if(ra.startsWith("OK")) {
                        System.out.println("\nAdmin status updated successfully.");
                    } else {
                        System.out.println("\nFailed to update admin status. Please try again later.");
                    }
                    break;
                case "7":
                    return;
                default:
                    System.out.println("\nInvalid option. Please try again.");
                    continue;
            }
        }
    }
}
