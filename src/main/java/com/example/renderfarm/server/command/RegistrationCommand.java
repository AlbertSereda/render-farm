package com.example.renderfarm.server.command;

import com.example.renderfarm.server.ClientSocket;
import com.example.renderfarm.server.command.CommandException.IncorrectCommandException;
import com.example.renderfarm.server.command.CommandException.LoginAlreadyExistsException;
import com.example.renderfarm.server.command.CommandException.NotPasswordMatchException;
import com.example.renderfarm.server.entity.Client;
import com.example.renderfarm.server.service.ClientService;

public class RegistrationCommand implements Command {

    private final ClientService clientService;

    public RegistrationCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public String execute(ClientSocket clientSocket, String[] clientMessage) {
        if (clientMessage.length != 4) {
            throw new IncorrectCommandException();
        }
        if (!clientMessage[2].equals(clientMessage[3])) {
            throw new NotPasswordMatchException();
        }
        Client client = clientService.findClientByLoginIgnoreCase(clientMessage[1]);
        if (client != null) {
            throw new LoginAlreadyExistsException();
        }
        client = new Client(clientMessage[1], clientMessage[2]);
        clientService.saveClient(client);
        return "Congratulations, the registration was successful";

    }

    @Override
    public String helpMessage() {
        return "reg <login> <password> <confirm password>\t :Register an account";
    }
}
