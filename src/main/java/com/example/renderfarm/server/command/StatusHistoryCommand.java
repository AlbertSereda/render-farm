package com.example.renderfarm.server.command;

import com.example.renderfarm.server.ClientSocket;
import com.example.renderfarm.server.command.CommandException.IncorrectCommandException;
import com.example.renderfarm.server.command.CommandException.NotAuthorizedClientException;
import com.example.renderfarm.server.entity.Client;
import com.example.renderfarm.server.entity.StatusHistory;
import com.example.renderfarm.server.service.StatusHistoryService;

import java.util.List;

public class StatusHistoryCommand implements Command {

    private final StatusHistoryService statusHistoryService;

    public StatusHistoryCommand(StatusHistoryService statusHistoryService) {
        this.statusHistoryService = statusHistoryService;
    }

    @Override
    public String execute(ClientSocket clientSocket, String[] clientMessage) {
        if (clientMessage.length != 1) {
            throw new IncorrectCommandException();
        }

        if (!clientSocket.isAuthorized()) {
            throw new NotAuthorizedClientException();
        }

        Client client = clientSocket.getClient();
        List<StatusHistory> statusHistories;
        statusHistories = statusHistoryService.findAllByClient(client);
        return getStringFromList(statusHistories);
    }

    public String getStringFromList(List<StatusHistory> statusHistories) {
        StringBuilder sb = new StringBuilder();
        sb.append("Id").append('\t');
        sb.append("NameTask").append('\t');
        sb.append("Status").append('\t');
        sb.append("Date").append('\t').append('\n');
        for (StatusHistory statusHistory : statusHistories) {
            sb.append(statusHistory.getId()).append('\t');
            sb.append(statusHistory.getTask().getName()).append('\t');
            sb.append(statusHistory.getStatus()).append('\t');
            sb.append(statusHistory.getDate()).append('\t').append('\n');
        }
        return sb.toString();
    }

    @Override
    public String helpMessage() {
        return "statusHistory\t :Show the history of changing task statuses";
    }
}
