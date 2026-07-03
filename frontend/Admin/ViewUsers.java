package frontend.Admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ViewUsers {
    public void viewUsers(String response, PrintWriter out, BufferedReader in, Scanner scanner) throws IOException {

        if(!response.startsWith("OK")) { 
            System.out.println("Could not retrieve users. Please try again later.");
            return;
        }

        String[] r = response.split("###");

        if(r.length == 0) {
            System.out.println("No users found.");
            return;
        }

        while(true) {
            System.out.println("\nDisplaying all registered users:");
            System.out.println("\nUsername\tEmail\t\tAdmin Status");

            for(int i = 1; i < r.length; i++) {
                String[] p = r[i].split("\\|\\|\\|");
                System.out.println(p[0] + "\t" + p[1] + "\t" + p[2]);
            }

            System.out.println("\nTotal users: " + (r.length - 1));

            System.out.println("\n1. User Details");
            System.out.println("2. Go back to the Admin Main Menu.");
        
            String c = scanner.nextLine();
        

            if(c.equals("1")) {
                System.out.println("\nEnter the username of the user you want to see details for:");
                String username = scanner.nextLine();

                out.println("USERDETAILS|||" + username);
                String ud = in.readLine();

                if(ud.startsWith("OK")) {
                    String[] userDetails = ud.split("\\|\\|\\|");
                    System.out.println("\nUser Details for: " + username);
                    System.out.println("Username\tEmail\t\tName\tSurname\t\tBirthdate\tGender\tAddress\t\tAdmin Status");
                    System.out.println(userDetails[1] + "\t" + userDetails[2] + "\t" + userDetails[3] + "\t" + userDetails[4] + "\t" + userDetails[5] + "\t" + userDetails[6] + "\t" + userDetails[7] + "\t" + userDetails[8]);
                    }   
                else if(ud.startsWith("ERROR")) {
                    System.out.println("\nUser not found. Please try again.");
                    continue;
                }
                } else if(c.equals("2")) {
                    System.out.println("\nReturning to the Admin Main Menu...");
                    break;
                } else {
                    System.out.println("\nInvalid option. Please try again.");
                    continue;
            }
        }
    }
}  
