package com.example.renderfarm.server;

import com.example.renderfarm.server.entity.Task;
import com.example.renderfarm.server.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ClientTaskExecutor {
    private final ExecutorService executorService;

    private final TaskService taskService;

    public ClientTaskExecutor(@Autowired TaskService taskService) {
        this.taskService = taskService;
        executorService = Executors.newCachedThreadPool();
    }

    public void executeTask(Task task, int executeTime) {
        executorService.execute(() -> {
            long startTime = System.currentTimeMillis();
            long currentTime = startTime;
            while ((currentTime - startTime) < executeTime) {
                // Imitation of work
                currentTime = System.currentTimeMillis();
            }
            task.setStatus(Task.Status.COMPLETE);
            taskService.save(task);
        });
    }
}
