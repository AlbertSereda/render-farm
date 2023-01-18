package com.example.renderfarm.server;

import com.example.renderfarm.server.command.Command;
import com.example.renderfarm.server.command.CommandInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Component
public class ServerLogicExecutor {

    private static final Logger log = Logger.getLogger(ServerLogicExecutor.class.getName());
    private static final int COUNT_THREAD = 1;
    private final ExecutorService executorService;

    @Autowired
    private MessageConverter messageConverter;
    private final Map<String, Command> commands;

    public ServerLogicExecutor(@Autowired CommandInitializer commandInitializer) {
        executorService = Executors.newFixedThreadPool(COUNT_THREAD);
        commands = commandInitializer.init();
        log.info("ServerLogicExecutor is started");
    }

    public void notifyNewClient(ClientSocket clientSocket) {
        executorService.execute(() -> {
            String message = "Welcome to the render farm";
            String response = messageConverter.convertMessageForClient(message);
            clientSocket.writeToBuffer(response.getBytes());
        });
    }

    public void executeCommand(ClientSocket clientSocket, String clientMessage) {
        executorService.execute(() -> {
            String[] splitMessage = clientMessage.trim().replaceAll(" +", " ").split(" ");
            String message = "";
            if (splitMessage.length >= 1) {
                if (commands.get(splitMessage[0].toLowerCase()) == null) {
                    message = "Incorrect command. Enter the \"help\" command to display a list of commands";
                } else {
                    Command command = commands.get(splitMessage[0].toLowerCase());
                    message = command.execute(clientSocket, splitMessage);
                }
            }
            String response = messageConverter.convertMessageForClient(message);
            clientSocket.writeToBuffer(response.getBytes());
        });
    }

}
