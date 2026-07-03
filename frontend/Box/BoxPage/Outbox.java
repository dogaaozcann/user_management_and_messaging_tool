package frontend.Box.BoxPage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import frontend.SeeMessage;
import frontend.Box.BoxDisplay.OutboxDisplay;

public class Outbox {
    public void outbox(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        
        OutboxDisplay outboxDisplay = new OutboxDisplay();
        int page = 1;

        while(true) {
            out.println("OUTBOX|||" + page);
            String response = in.readLine();

            System.out.println("\nOutbox - Page " + page);

            System.out.println("-> ID\t\tReceiver\tSubject\t\tTime");

            outboxDisplay.outboxDisplay(response);

            System.out.println("\n1. Next Page");
            System.out.println("2. Previous Page");
            System.out.println("3. Read Message");
            System.out.println("4. Exit Outbox");
            String c = scanner.nextLine();

            if (page == 1 && c.equals("2")) {
                System.out.println("Note: You are on the first page. Previous page is not available.");
            } else if (c.equals("1")) {
                page++;
            } else if (c.equals("2")) {
                page--;
            } else if (c.equals("3")) {
                System.out.println("\nEnter the message ID to read:");
                String messageId = scanner.nextLine();  
                out.println("SEEMSG|||" + messageId);
                String idResponse = in.readLine();
                new SeeMessage().seeMessage(idResponse);

            } else if (c.equals("4")) {
                System.out.println("\nExiting Outbox...");
                break;
            } else {
                System.out.println("\nInvalid option. Please try again.");
            }
        }
   }
}