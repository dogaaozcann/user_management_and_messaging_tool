package frontend;

import java.util.Scanner;

public class MainMenu {
    public String mainMenu(Scanner scanner) {

        System.out.println("\nWelcome to the User Management and Messaging Tool!");
        System.out.println("==============================================");
        while (true) {
            System.out.println("Please choose an option to continue:");
            System.out.println("1. Register - If you are a new user.");
            System.out.println("2. Login - If you already have an account.");
            System.out.println("3. Exit");
            String c = scanner.nextLine();

            switch (c) {
                case "1":
                    return new Register().registerUser(scanner);

                case "2":
                    return new Login().loginUser(scanner);

                case "3":
                    System.out.println("Exiting...");
                    scanner.close();
                    return "SHUTDOWN";
                        
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }  
    }
}
