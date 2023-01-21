package com.example.renderfarm.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;


class ClientTest {

    Client client = new Client();

    @Test
    void writeMessage() {
        String messageToServer = "test meaasge";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(baos));
        try {
            client.writeMessage(bw, messageToServer);
        } catch (IOException e) {
        }

        Assertions.assertEquals(messageToServer + "\n", baos.toString());
    }

    @Test
    void readMessage() {

        String oneLine = "1\nOne Line";
        BufferedReader brOneLine = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(oneLine.getBytes(StandardCharsets.UTF_8))));

        String twoLine = "2\nOne Line\nTwoLine";
        BufferedReader brTwoLine = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(twoLine.getBytes(StandardCharsets.UTF_8))));

        String readOneLine = "";
        String readTwoLine = "";
        try {
            readOneLine = client.readMessage(brOneLine);
            readTwoLine = client.readMessage(brTwoLine);
        } catch (IOException e) {
        }

        Assertions.assertEquals("One Line", readOneLine);
        Assertions.assertEquals("One Line\nTwoLine", readTwoLine);
    }

}