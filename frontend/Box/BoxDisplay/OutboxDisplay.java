package frontend.Box.BoxDisplay;

public class OutboxDisplay {
    public void outboxDisplay(String response) { 
           
        if(!response.startsWith("OK")) { 
            System.out.println("\nCould not retrieve messages. Please try again.");
            return;    
        }

        String[] messages = response.split("###");

        if(messages.length == 0) {
            System.out.println("\nNo messages found.");
            return;
        }

        for(int i = 1; i < messages.length; i++) {
            String[] m = messages[i].split("\\|\\|\\|");
            System.out.println(m[1] + "\t" + m[2] + "\t\t" + m[3]);
        }
    }
}  