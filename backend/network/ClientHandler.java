package backend.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import backend.src.Data.Session;

public class ClientHandler implements Runnable {
    private final Socket s;
    private final Dispatcher d;

    public ClientHandler(Socket s, Dispatcher d) {
        this.s = s;
        this.d = d;
    }

    Session session = new Session(); //User that logged in through this clientHandler.

    @Override
    public void run() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter out = null;
        try {
            out = new PrintWriter(s.getOutputStream(), true, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line;
        try {
            while ((line = in.readLine()) != null) {   
                System.out.println("Received: " + line);
                out.println(d.handleRequest(session, line));          
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}