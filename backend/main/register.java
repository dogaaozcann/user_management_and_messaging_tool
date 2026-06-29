package backend.main;

import backend.src.Data.User;
import java.util.Scanner;
import backend.src.Db.Database;
import backend.src.Service.UserService;

public class register {
    void registerUser() {
        Database db = new Database();
        db.createTables();
        UserService userService = new UserService(db);
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("\nWelcome to the User Registration. Type ESC to exit at any time.");

            while (!scanner.nextLine().equals("ESC")) {

                User newUser = new User();


                System.out.print("Enter username: ");
                String enteredUsernameRegister = scanner.nextLine();
                    if (userService.searchUser(enteredUsernameRegister)) {
                        System.out.println("Username already exists. Please choose a different username.");
                        continue;
                    }
                    if (enteredUsernameRegister.trim().isEmpty()) {
                        System.out.println("Username cannot be empty. Please enter a valid username.");
                        continue;
                    }
                newUser.setUsername(enteredUsernameRegister);

                System.out.print("Enter email: ");
                String enteredEmail = scanner.nextLine();
                    if (userService.searchEmail(enteredEmail)) {
                        System.out.println("Email already exists. Please choose a different email.");
                        continue;
                    }
                    if (enteredEmail.trim().isEmpty()) {
                        System.out.println("Email cannot be empty. Please enter a valid email.");
                        continue;
                    }
                newUser.setEmail(enteredEmail);

                System.out.print("Enter name: ");
                String enteredName = scanner.nextLine();
                    if (enteredName.trim().isEmpty()) {
                        System.out.println("Name cannot be empty. Please enter a valid name.");
                        continue;
                    }
                newUser.setName(enteredName);

                System.out.print("Enter surname: ");
                String enteredSurname = scanner.nextLine();
                    if (enteredSurname.trim().isEmpty()) {
                        System.out.println("Surname cannot be empty. Please enter a valid surname.");
                        continue;
                    }
                newUser.setSurname(enteredSurname);

                System.out.print("Enter birthdate (DD-MM-YYYY): ");
                newUser.setBirthdate(scanner.nextLine());

                System.out.print("Enter gender (M/F/Other): ");
                newUser.setGender(scanner.nextLine());
                        
                System.out.print("Enter address: ");
                String enteredAddress = scanner.nextLine();
                    if (enteredAddress.trim().isEmpty()) {
                        System.out.println("Address cannot be empty. Please enter a valid address.");
                        continue;
                    }
                newUser.setAddress(enteredAddress);

                System.out.print("Enter password: ");
                String enteredPassword = scanner.nextLine();
                    if (enteredPassword.trim().isEmpty()) {
                        System.out.println("Password cannot be empty. Please enter a valid password.");
                        continue;
                    }
                newUser.setPassword(enteredPassword);

                    if (userService.registerUser(newUser)) {
                        System.out.println("User registered successfully.\nLogging in...");
                    } else {
                        System.out.println("User registration failed. Please try again.");
                    }
            }
        }
    }
}
