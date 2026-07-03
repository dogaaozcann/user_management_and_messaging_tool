package frontend;

public class ReadMessage {
    public void readMessage(String message) {

        if(!message.startsWith("OK")) { 
            System.out.println("\nCould not retrieve message. Please try again.");
            return;    
        }

        String[] m = message.split("\\|\\|\\|");


        System.out.println("\nMessage Details:\n");
        System.out.println("From: " + m[2]);
        System.out.println("Time: " +m[5]);
        System.out.println("Subject: " + m[3]);
        System.out.println("Message: " + m[4]);
    }
    
}
