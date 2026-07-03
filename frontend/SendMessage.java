package frontend;

import java.util.Scanner;

public class SendMessage {
    public String sendMessage(Scanner scanner) {
        System.out.println("\nSend Message:");

        System.out.print("Enter recipient: ");
        String recipient = scanner.nextLine();

        System.out.print("Enter subject: ");
        String subject = scanner.nextLine();

        System.out.print("Enter message: ");
        String message = scanner.nextLine();
        return "SENDMSG|||" + recipient + "|||" + subject + "|||" + message + "|||";
    }
}
