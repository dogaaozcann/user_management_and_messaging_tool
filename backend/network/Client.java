package backend.network;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8000);
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8)); 
        BufferedReader in  = new BufferedReader(new InputStreamReader(socket.getInputStream(),StandardCharsets.UTF_8));
        PrintWriter    out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        while(true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("ESC")) {
                break;
            }
            out.println(input);
            System.out.println("Server response: " + in.readLine());
        }
        socket.close();
        scanner.close();
    }
}