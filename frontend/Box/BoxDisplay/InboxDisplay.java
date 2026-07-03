package frontend.Box.BoxDisplay;

public class InboxDisplay {
    public void inboxDisplay(String response) { 
           
        if(!response.startsWith("OK")) { 
            System.out.println("\nCould not retrieve messages. Please try again.");
            return;    
        }

        String[] messages = response.split("###");

        if(messages.length <= 1) {
            System.out.println("\nNo messages found.");
            return;
        }

        for(int i = 1; i < messages.length; i++) {
            String[] m = messages[i].split("\\|\\|\\|");
            String read = Boolean.parseBoolean(m[4]) ? "✓" : "x";
            System.out.println(m[0] + "\t" + m[1] + "\t" + m[2] + "\t" + m[3] + "\t" + read);
        }
    }
}
