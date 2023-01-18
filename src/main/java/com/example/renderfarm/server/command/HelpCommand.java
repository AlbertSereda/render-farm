package com.example.renderfarm.server.command;

import com.example.renderfarm.server.ClientSocket;

import java.util.Map;

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
        return helpMessage;
    }

    @Override
    public String helpMessage() {
        return "help\t :Show the list of commands";
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
