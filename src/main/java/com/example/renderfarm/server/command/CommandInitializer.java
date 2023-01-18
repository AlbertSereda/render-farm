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

    @Autowired
    private ClientTaskExecutor taskExecutor;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private StatusHistoryService statusHistoryService;

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
