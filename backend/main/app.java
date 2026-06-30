package backend.main;

import java.util.Scanner;

public class app {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWelcome to the User Management and Messaging Tool!");
        System.out.println("==============================================");
        while (true) {
            System.out.println("\nPlease choose an option to continue:");
            System.out.println("1. Register - If you are a new user.");
            System.out.println("2. Login - If you already have an account.");
            System.out.println("3. Exit");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    register register = new register();
                    register.registerUser(scanner);
                    break;

                case "2":
                    login login = new login();
                    login.loginUser(scanner);
                    break;

                case "3":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                        
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }  
    }
}
