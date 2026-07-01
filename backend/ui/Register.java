package backend.ui;

import backend.src.Data.User;
import java.util.Scanner;
import backend.src.Db.Database;
import backend.src.Service.UserService;

public class Register {
    void registerUser(Scanner scanner) {
        Database db = new Database();
        db.createTables();
        UserService userService = new UserService(db);

            System.out.println("\nWelcome to the User Registration. Press enter to continue or type ESC to exit.");

            while (!scanner.nextLine().equalsIgnoreCase("ESC")) {

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

                System.out.print("Enter birthdate (YYYY-MM-DD): ");
                newUser.setBirthdate(scanner.nextLine());
                    if (newUser.getBirthdate().trim().isEmpty()) {
                        System.out.println("Birthdate cannot be empty. Please enter a valid birthdate.");
                        continue;
                    }

                System.out.print("Enter gender (F/M/Other): ");
                newUser.setGender(scanner.nextLine());

                    if(!newUser.getGender().equals("F") && !newUser.getGender().equals("M") && !newUser.getGender().equals("Other")) {
                        System.out.println("Invalid gender. Please enter a valid gender.");
                        continue;
                    }
                    if(newUser.getGender().trim().isEmpty()) {
                        System.out.println("Gender cannot be empty. Please enter a valid gender.");
                        continue;
                    }

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

                if (userService.registerUser(newUser) != null) {
                    return;
                }

            }
    }
}
