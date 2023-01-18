package com.example.renderfarm.server.command;

import com.example.renderfarm.server.ClientSocket;
import com.example.renderfarm.server.ClientTaskExecutor;
import com.example.renderfarm.server.entity.Client;
import com.example.renderfarm.server.entity.Task;
import com.example.renderfarm.server.service.TaskService;

import java.text.DecimalFormat;

public class CreateTaskCommand implements Command {
    private final TaskService taskService;
    private final ClientTaskExecutor taskExecutor;

    public CreateTaskCommand(TaskService taskService, ClientTaskExecutor taskExecutor) {
        this.taskService = taskService;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public String execute(ClientSocket clientSocket, String[] clientMessage) {
        if (clientMessage.length != 2) {
            return "Incorrect command";
        }

        if (!clientSocket.isAuthorized()) {
            return "You must log in to execute this command";
        }

        Client client = clientSocket.getClient();
        Task task = new Task(clientMessage[1], Task.Status.RENDERING, client);
        client.addTask(task);
        taskService.save(task);
        int executeTime = (int) ((Math.random() * (300000 - 60000)) + 60000);
        String executeTimeInMin = new DecimalFormat("0.00").format(executeTime / 1000. / 60);
        taskExecutor.executeTask(task, executeTime);
        return "The task has been accepted for processing and will be completed in " + executeTimeInMin
                + " min. Id task = " + task.getId() + ", NameTask = " + task.getName();
    }

    @Override
    public String helpMessage() {
        return "createTask <nameTask>\t :Create a task";
    }
}
