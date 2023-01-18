package com.example.renderfarm.server.service;

import com.example.renderfarm.server.entity.Client;
import com.example.renderfarm.server.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> findAllByClient(Client client);

    List<Task> findAllByClientAndName(Client client, String name);

    List<Task> findAllByClientAndId(Client client, int id);

    List<Task> findAllByClientAndStatus(Client client, Task.Status status);

    Task save(Task task);
}
