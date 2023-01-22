package com.example.renderfarm.server.command;

import com.example.renderfarm.server.ClientSocket;
import com.example.renderfarm.server.command.CommandException.IncorrectCommandException;
import com.example.renderfarm.server.command.CommandException.NotAuthorizedClientException;
import org.springframework.stereotype.Component;

@CommandAnnotation
@Component
public class LogOutCommand implements Command {

    @Override
    public String execute(ClientSocket clientSocket, String[] clientMessage) {
        if (clientMessage.length != 1) {
            throw new IncorrectCommandException();
        }

        if (!clientSocket.isAuthorized()) {
            throw new NotAuthorizedClientException();
        }
        clientSocket.setClient(null);
        clientSocket.setAuthorized(false);
        return "You are logged out";
    }

    @Override
    public String helpMessage() {
        return "logOut\t :Log out of your account";
    }

    @Override
    public String getNameCommand() {
        return "logout";
    }
}
