package frontend.Box.BoxPage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import frontend.ReadMessage;
import frontend.Box.BoxDisplay.InboxDisplay;

public class Inbox {
    public void inbox(Scanner scanner, PrintWriter out, BufferedReader in) throws IOException {
        
        InboxDisplay inboxDisplay = new InboxDisplay();
        int page = 1;

        while(true) {
            out.println("INBOX|||" + page);
            String response = in.readLine();

            System.out.println("\nInbox - Page " + page);

            System.out.println("Sender\tSubject\tTime\tStatus");

            inboxDisplay.inboxDisplay(response);

            System.out.println("\n1. Next Page");
            System.out.println("2. Previous Page");
            System.out.println("3. Read Message");
            System.out.println("4. Exit Inbox");
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
                out.println("READMSG|||" + messageId);
                String idResponse = in.readLine();
                new ReadMessage().readMessage(idResponse);
            } else if (c.equals("4")) {
                System.out.println("\nExiting Inbox...");
                break;
            } else {
                System.out.println("\nInvalid option. Please try again.");
            }


    }
}}
