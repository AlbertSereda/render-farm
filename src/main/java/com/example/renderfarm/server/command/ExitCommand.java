package com.example.renderfarm.server.command;

import com.example.renderfarm.server.ClientSocket;

public class ExitCommand implements Command {
    @Override
    public String execute(ClientSocket clientSocket, String[] clientMessage) {
        return "exit";
    }

    @Override
    public String helpMessage() {
        return "exit\t :Exit the application";
    }
}
