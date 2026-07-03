package frontend.Admin;

public class ViewUsers {
    public void viewUsers(String response) {

        if(!response.startsWith("OK")) { 
            System.out.println("Could not retrieve users. Please try again later.");
            return;
        }

        String[] r = response.split("###");

        if(r.length == 0) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("\nDisplaying all registered users:");
        System.out.println("\nUsername\tEmail\t\tAdmin Status");

        for(int i = 1; i < r.length; i++) {
            String[] p = r[i].split("\\|\\|\\|");
            System.out.println(p[0] + "\t" + p[1] + "\t" + p[2]);
        }

        System.out.println("\nTotal users: " + (r.length - 1));

        System.out.println("\n1. User Details");
        System.out.println("2. Go back to the Admin Main Menu.");
    }  
}
