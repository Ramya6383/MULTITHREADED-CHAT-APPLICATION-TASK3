package chatapp;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Connected to chat server.");

        new ReadThread(socket).start();
        new WriteThread(socket).start();
    }

    static class ReadThread extends Thread {
        private BufferedReader in;

        public ReadThread(Socket socket) throws IOException {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        public void run() {
            try {
                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                }
            } catch (IOException e) {
                System.out.println("Connection closed.");
            }
        }
    }

    static class WriteThread extends Thread {
        private PrintWriter out;
        private BufferedReader consoleReader;

        public WriteThread(Socket socket) throws IOException {
            out = new PrintWriter(socket.getOutputStream(), true);
            consoleReader = new BufferedReader(new InputStreamReader(System.in));
        }

        public void run() {
            try {
                String input;
                while ((input = consoleReader.readLine()) != null) {
                    out.println(input);
                }
            } catch (IOException e) {
                System.out.println("Error writing to server.");
            }
        }
    }
}
