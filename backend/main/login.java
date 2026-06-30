package backend.main;

import java.util.Scanner;
import backend.src.Db.Database;
import backend.src.Data.User;
import backend.src.Service.UserService;

public class login {
    public void loginUser(Scanner scanner) {
        Database db = new Database();
        db.createTables();
        UserService userService = new UserService(db);

            System.out.println("\nWelcome to the User Login. Press enter to continue or type ESC to exit.");

            while(!scanner.nextLine().equals("ESC")){

                System.out.print("Enter username: ");
                String enteredUsernameLogin = scanner.nextLine();
                    if (!userService.searchUser(enteredUsernameLogin)) {
                        System.out.println("Username does not exist. Please try again.");
                        continue;
                    }
                    if (enteredUsernameLogin.trim().isEmpty()) {
                        System.out.println("Username cannot be empty. Please enter a valid username.");
                        continue;
                    }

                System.out.print("Enter password: ");
                String enteredPassword = scanner.nextLine();
                    if (enteredPassword.trim().isEmpty()) {
                        System.out.println("Password cannot be empty. Please enter a valid password.");
                        continue;
                    }
                User currentUser = userService.loginUser(enteredUsernameLogin, enteredPassword);
                    if (currentUser != null) {
                        return;
                    } else {
                        continue;
                    }
            }
    }
}   
