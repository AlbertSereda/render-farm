package com.example.renderfarm.server.command;

import com.example.renderfarm.server.ClientTaskExecutor;
import com.example.renderfarm.server.service.ClientService;
import com.example.renderfarm.server.service.StatusHistoryService;
import com.example.renderfarm.server.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandInitializer {

    private final ClientTaskExecutor taskExecutor;

    private final ClientService clientService;

    private final TaskService taskService;

    private final StatusHistoryService statusHistoryService;

    public CommandInitializer(@Autowired ClientTaskExecutor taskExecutor,
                              @Autowired ClientService clientService,
                              @Autowired TaskService taskService,
                              @Autowired StatusHistoryService statusHistoryService) {
        this.taskExecutor = taskExecutor;
        this.clientService = clientService;
        this.taskService = taskService;
        this.statusHistoryService = statusHistoryService;
    }

    public Map<String, Command> init() {
        Map<String, Command> commands = new HashMap<>();

        commands.put("reg", new RegistrationCommand(clientService));
        commands.put("login", new LogInCommand(clientService));
        commands.put("logout", new LogOutCommand());
        commands.put("createtask", new CreateTaskCommand(taskService, taskExecutor));
        commands.put("listtasks", new ListTasksCommand(taskService));
        commands.put("exit", new ExitCommand());
        commands.put("help", new HelpCommand(commands));
        commands.put("statushistory", new StatusHistoryCommand(statusHistoryService));

        return commands;
    }
}
