package com.example.renderfarm.server.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandInitializer {

    private final ApplicationContext appContext;

    public CommandInitializer(@Autowired ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public Map<String, Command> init() {

        Map<String, Object> commandsObject = appContext.getBeansWithAnnotation(CommandAnnotation.class);
        Map<String, Command> commands = new HashMap<>();

        commandsObject.forEach((s, o) -> commands.put(((Command) o).getNameCommand(), (Command) o));

        return commands;
    }
}
