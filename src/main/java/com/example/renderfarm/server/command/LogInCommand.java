package com.example.renderfarm.server.command;

import com.example.renderfarm.server.ClientSocket;
import com.example.renderfarm.server.command.CommandException.ClientAlreadyLoggedException;
import com.example.renderfarm.server.command.CommandException.IncorrectCommandException;
import com.example.renderfarm.server.command.CommandException.InvalidLoginPasswordException;
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
            throw new IncorrectCommandException();
        }

        if (clientSocket.isAuthorized()) {
            throw new ClientAlreadyLoggedException();
        }

        Client client = clientService.findByLoginIgnoreCase(clientMessage[1]);

        if (client == null || !client.getPassword().equals(clientMessage[2])) {
            throw new InvalidLoginPasswordException();
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
