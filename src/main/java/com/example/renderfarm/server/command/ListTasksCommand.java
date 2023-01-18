package com.example.renderfarm.server.command;

import com.example.renderfarm.server.ClientSocket;
import com.example.renderfarm.server.entity.Client;
import com.example.renderfarm.server.entity.Task;
import com.example.renderfarm.server.service.TaskService;

import java.util.ArrayList;
import java.util.List;

public class ListTasksCommand implements Command {
    private final TaskService taskService;

    public ListTasksCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public String execute(ClientSocket clientSocket, String[] clientMessage) {
        if (clientMessage.length != 1 && clientMessage.length != 3) {
            return "Incorrect command";
        }

        if (!clientSocket.isAuthorized()) {
            return "You must log in to execute this command";
        }

        Client client = clientSocket.getClient();
        List<Task> tasks = new ArrayList<>();

        if (clientMessage.length == 1) {
            tasks = taskService.findAllByClient(client);
        } else {
            switch (clientMessage[1].toLowerCase()) {
                case "-n":
                    String name = clientMessage[2];
                    tasks = taskService.findAllByClientAndName(client, name);
                    break;
                case "-i":
                    try {
                        int id = Integer.parseInt(clientMessage[2]);
                        tasks = taskService.findAllByClientAndId(client, id);
                    } catch (NumberFormatException e) {
                        return "Incorrect Id";
                    }
                    break;
                case "-s":
                    String status = clientMessage[2];
                    Task.Status[] statuses = Task.Status.values();
                    for (int i = 0; i < statuses.length; i++) {
                        if (statuses[i].toString().equalsIgnoreCase(status)) {
                            tasks = taskService.findAllByClientAndStatus(client, statuses[i]);
                            break;
                        }
                        if (i == statuses.length - 1) return "Incorrect Status";
                    }
                    break;
                default:
                    return "Incorrect parameter";
            }
        }
        return getStringFromList(tasks);
    }

    public String getStringFromList(List<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Id").append('\t');
        sb.append("NameTask").append('\t');
        sb.append("Status").append('\t').append('\n');
        for (Task task : tasks) {
            sb.append(task.getId()).append('\t');
            sb.append(task.getName()).append('\t');
            sb.append(task.getStatus()).append('\t').append('\n');
        }
        return sb.toString();
    }

    @Override
    public String helpMessage() {
        return "listTasks\t :Display the task list\n" +
                "listTasks -n <NameTask>\t :Display a list of tasks with filtering by task name\n" +
                "listTasks -i <IdTask>\t :Display a list of tasks with filtering by task ID\n" +
                "listTasks -s <StatusTask>\t :Display a list of tasks with filtering by task status";
    }
}
