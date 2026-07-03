package frontend;

import java.util.Scanner;

public class MainMenuUser {
    public String mainMenuUser(Scanner scanner, boolean isAdmin) {
        System.out.println("\nWelcome to the User Main Menu.");
        System.out.println("==============================================");
        while (true) {
            System.out.println("\nPlease choose an option to continue:");
            System.out.println("1. INBOX - View your messages.");
            System.out.println("2. OUTBOX - View sent messages.");
            System.out.println("3. Send Message");
            System.out.println("4. LOGOUT");

            if (isAdmin) {
                System.out.println("\n0: Admin Menu");
            }

            String c = scanner.nextLine();
            
            switch (c) {
                case "1":
                    return "INBOX";

                case "2":
                    return "OUTBOX";

                case "3":
                    return new SendMessage().sendMessage(scanner);

                case "4":
                    System.out.println("\nYou're logging out. Goodbye!");
                    return "LOGOUT";

                case "0":
                    if (isAdmin) {
                        return "ADMINMENU";
                    } else {
                        System.out.println("\nInvalid option. Please try again.");
                        break;
                    }
                        
                default:
                    System.out.println("\nInvalid option. Please try again.");
            }
        }
    }
}