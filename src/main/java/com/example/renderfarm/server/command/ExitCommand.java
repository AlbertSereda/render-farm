package com.example.renderfarm.server.command;

import com.example.renderfarm.server.ClientSocket;
import com.example.renderfarm.server.command.CommandException.IncorrectCommandException;
import org.springframework.stereotype.Component;

@CommandAnnotation
@Component
public class ExitCommand implements Command {
    @Override
    public String execute(ClientSocket clientSocket, String[] clientMessage) {
        if (clientMessage.length != 1) {
            throw new IncorrectCommandException();
        }
        return "exit";
    }

    @Override
    public String helpMessage() {
        return "exit\t :Exit the application";
    }

    @Override
    public String getNameCommand() {
        return "exit";
    }
}
