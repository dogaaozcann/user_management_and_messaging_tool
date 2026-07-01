package backend.network;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

import backend.src.Db.Database;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000); 
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.out.println("Server started. Waiting for clients to connect...");

        Database db = new Database();
        db.createTables();
        Dispatcher d = new Dispatcher(db);
        
        while(true){
            try {
                Socket clientSocket = serverSocket.accept(); 
                System.out.println("Client connected.");
                ClientHandler ch = new ClientHandler(clientSocket, d);
                new Thread(ch).start();
            }
            catch (IOException e) {
                e.printStackTrace();
                try {
                    serverSocket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
}