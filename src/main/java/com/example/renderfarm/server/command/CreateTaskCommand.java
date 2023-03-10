package com.example.renderfarm.server.command;

import com.example.renderfarm.server.ClientSocket;
import com.example.renderfarm.server.ClientTaskExecutor;
import com.example.renderfarm.server.command.CommandException.IncorrectCommandException;
import com.example.renderfarm.server.command.CommandException.NotAuthorizedClientException;
import com.example.renderfarm.server.entity.Client;
import com.example.renderfarm.server.entity.Task;
import com.example.renderfarm.server.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@CommandAnnotation
@Component
public class CreateTaskCommand implements Command {
    private final TaskService taskService;
    private final ClientTaskExecutor taskExecutor;

    public CreateTaskCommand(@Autowired TaskService taskService,
                             @Autowired ClientTaskExecutor taskExecutor) {
        this.taskService = taskService;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public String execute(ClientSocket clientSocket, String[] clientMessage) {
        if (clientMessage.length < 2) {
            throw new IncorrectCommandException();
        }

        if (!clientSocket.isAuthorized()) {
            throw new NotAuthorizedClientException();
        }

        Client client = clientSocket.getClient();
        StringBuilder nameTask = new StringBuilder();
        for (int i = 1; i < clientMessage.length; i++) {
            nameTask.append(clientMessage[i]);
            if (i != clientMessage.length - 1) nameTask.append(" ");
        }
        Task task = new Task(nameTask.toString(), Task.Status.RENDERING, client);
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

    @Override
    public String getNameCommand() {
        return "createtask";
    }
}
