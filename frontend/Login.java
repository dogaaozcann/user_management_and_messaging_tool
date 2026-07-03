package frontend;

import java.util.Scanner;

public class Login {
    public String loginUser(Scanner scanner) {

        System.out.println("\nWelcome to the User Login. Press enter to continue or type ESC to exit.");

        System.out.print("Enter username: ");
        String enteredUsername = scanner.nextLine();
            if (enteredUsername.trim().isEmpty()) {
                System.out.println("Username cannot be empty. Please enter a valid username.");
                return "EXIT";
            } 
            if (enteredUsername.equalsIgnoreCase("ESC")) {
                return "EXIT";
            }

        System.out.print("Enter password: ");
        String enteredPassword = scanner.nextLine();
            if (enteredPassword.trim().isEmpty()) {
                System.out.println("Password cannot be empty. Please enter a valid password.");
                return "EXIT";
            }
            if (enteredPassword.equalsIgnoreCase("ESC")) {
                return "EXIT";
            }

        return "LOGIN|||" + enteredUsername + "|||" + enteredPassword + "|||";
    }
}   
