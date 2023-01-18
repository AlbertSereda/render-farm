package com.example.renderfarm.server.command;

import com.example.renderfarm.server.ClientSocket;
import com.example.renderfarm.server.entity.Client;
import com.example.renderfarm.server.service.ClientService;

public class LogInCommand implements Command {

    private final ClientService clientService;

    public LogInCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public String execute(ClientSocket clientSocket, String[] clientMessage) {
        if (clientMessage.length != 3) {
            return "Incorrect command";
        }

        if (clientSocket.isAuthorized()) {
            return "You are already logged in";
        }

        Client client = clientService.findByLogin(clientMessage[1]);

        if (client == null || !client.getPassword().equals(clientMessage[2])) {
            return "Invalid Login or Password";
        }

        clientSocket.setAuthorized(true);
        clientSocket.setClient(client);
        return "Welcome, " + client.getLogin() + "!";
    }

    @Override
    public String helpMessage() {
        return "login <login> <password>\t :Log in to your account";
    }
}
