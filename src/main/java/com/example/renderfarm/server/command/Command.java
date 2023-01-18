package com.example.renderfarm.server.command;

import com.example.renderfarm.server.ClientSocket;

public interface Command {

    String execute(ClientSocket clientSocket, String[] clientMessage);

    String helpMessage();
}
