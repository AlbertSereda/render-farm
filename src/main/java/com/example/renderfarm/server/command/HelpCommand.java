package com.example.renderfarm.server.command;

import com.example.renderfarm.server.ClientSocket;
import com.example.renderfarm.server.command.CommandException.IncorrectCommandException;
import org.springframework.stereotype.Component;

import java.util.Map;

@CommandAnnotation
@Component
public class HelpCommand implements Command {

    private final Map<String, Command> commands;

    private String helpMessage;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public String execute(ClientSocket clientSocket, String[] clientMessage) {
        if (helpMessage == null) {
            helpMessage = fillHelpMessage();
        }
        if (clientMessage.length != 1) {
            throw new IncorrectCommandException();
        }
        return helpMessage;
    }

    @Override
    public String helpMessage() {
        return "help\t :Show the list of commands";
    }

    @Override
    public String getNameCommand() {
        return "help";
    }

    private String fillHelpMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("List of commands:\n");
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            stringBuilder.append(entry.getValue().helpMessage()).append('\n');
        }
        return stringBuilder.toString();
    }
}
