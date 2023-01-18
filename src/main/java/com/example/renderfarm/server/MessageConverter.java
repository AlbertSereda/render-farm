package com.example.renderfarm.server;

import org.springframework.stereotype.Component;

@Component
public class MessageConverter {

    public String convertMessageForClient(String message) {
        String[] messageSplit = message.split("\n");
        StringBuilder sb = new StringBuilder();
        sb.append(messageSplit.length).append('\n');
        for (String str : messageSplit) {
            sb.append(str).append('\n');
        }
        return sb.toString();
    }
}
