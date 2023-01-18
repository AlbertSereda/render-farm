package com.example.renderfarm.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader br = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             BufferedWriter bw = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream()));
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                String readString = br.readLine().replaceAll("\\D", "");
                int countLine = Integer.parseInt(readString);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < countLine; i++) {
                    sb.append(br.readLine()).append('\n');
                }
                System.out.println(sb);
                if (sb.toString().startsWith("exit")) {
                    break;
                } else {
                    String clientMessage = scanner.nextLine();
                    bw.write(clientMessage);
                    bw.write('\n');
                    bw.flush();
                }
            }

        } catch (IOException e) {
            System.out.println("Server connection error");
        }
    }
}
