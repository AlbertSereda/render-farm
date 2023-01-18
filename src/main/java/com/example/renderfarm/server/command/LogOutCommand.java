package com.example.renderfarm.server.command;

import com.example.renderfarm.server.ClientSocket;

public class LogOutCommand implements Command {

    @Override
    public String execute(ClientSocket clientSocket, String[] clientMessage) {
        if (clientMessage.length != 1) {
            return "Incorrect command";
        }

        if (!clientSocket.isAuthorized()) {
            return "You are not logged in";
        }
        clientSocket.setClient(null);
        clientSocket.setAuthorized(false);
        return "You are logged out";
    }

    @Override
    public String helpMessage() {
        return "logOut\t :Log out of your account";
    }
}
