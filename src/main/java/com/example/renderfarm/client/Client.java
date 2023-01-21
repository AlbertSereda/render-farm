package com.example.renderfarm.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        Client client = new Client();
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader br = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream()));
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                String message = client.readMessage(br);
                System.out.println(message);
                if (message.startsWith("exit")) {
                    break;
                } else {
                    String clientMessage = scanner.nextLine();
                    client.writeMessage(bw, clientMessage);
                }
            }
        } catch (IOException e) {
            System.out.println("Server connection error");
        }
    }

    public String readMessage(BufferedReader br) throws IOException {
        String readString = br.readLine().replaceAll("\\D", "");
        int countLine = Integer.parseInt(readString);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < countLine; i++) {
            sb.append(br.readLine());
            if (i < countLine - 1) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    public void writeMessage(BufferedWriter bw, String clientMessage) throws IOException {
        bw.write(clientMessage);
        bw.write('\n');
        bw.flush();
    }
}
