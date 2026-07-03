package frontend;

import java.util.Scanner;

public class Register {
    public String registerUser(Scanner scanner) {

        System.out.println("\nWelcome to the User Registration. Press enter to continue or type ESC to exit.");

        System.out.println("Enter username: ");
        String enteredUsername = scanner.nextLine();
        if (enteredUsername.isEmpty()) {
            System.out.println("Username is required. Please try again.");
            return "EXIT";
        }
        if (enteredUsername.equalsIgnoreCase("ESC")) {
            return "EXIT";
        }

        System.out.println("Enter email: ");
        String enteredEmail = scanner.nextLine();
        if (enteredEmail.isEmpty()) {
            System.out.println("Email is required. Please try again.");
            return "EXIT";
        }
        if (enteredEmail.equalsIgnoreCase("ESC")) {
            return "EXIT";
        }

        System.out.println("Enter name: ");
        String enteredName = scanner.nextLine();
        if (enteredName.isEmpty()) {
            System.out.println("Name is required. Please try again.");
            return "EXIT";
        }
        if (enteredName.equalsIgnoreCase("ESC")) {
            return "EXIT";
        }

        System.out.println("Enter surname: ");
        String enteredSurname = scanner.nextLine();
        if (enteredSurname.isEmpty()) {
            System.out.println("Surname is required. Please try again.");
            return "EXIT";
        }
        if (enteredSurname.equalsIgnoreCase("ESC")) {
            return "EXIT";
        }

        System.out.println("Enter birthdate (YYYY-MM-DD): ");
        String enteredBirthdate = scanner.nextLine();
        if (enteredBirthdate.isEmpty()) {
            System.out.println("Birthdate is required. Please try again.");
            return "EXIT";
        }
        if (enteredBirthdate.equalsIgnoreCase("ESC")) {
            return "EXIT";
        }

        System.out.println("Enter gender (F/M/Other): ");
        String enteredGender = scanner.nextLine();
        if (enteredGender.isEmpty()) {
            System.out.println("Gender is required. Please try again.");
            return "EXIT";
        }
        if (enteredGender.equalsIgnoreCase("ESC")) {
            return "EXIT";
        }

        System.out.println("Enter address: ");
        String enteredAddress = scanner.nextLine();
        if (enteredAddress.isEmpty()) {
            System.out.println("Address is required. Please try again.");
            return "EXIT";
        }
        if (enteredAddress.equalsIgnoreCase("ESC")) {
            return "EXIT";
        }

        System.out.println("Enter password: ");
        String enteredPassword = scanner.nextLine();
        if (enteredPassword.isEmpty()) {
            System.out.println("Password is required. Please try again.");
            return "EXIT";
        }
        if (enteredPassword.equalsIgnoreCase("ESC")) {
            return "EXIT";
        }
        return "REGISTER|||" + enteredUsername + "|||" + enteredEmail + "|||" + enteredName 
        + "|||" + enteredSurname + "|||" + enteredBirthdate + "|||" 
        + enteredGender + "|||" + enteredAddress + "|||" + enteredPassword + "|||";
    }
}
            
    

