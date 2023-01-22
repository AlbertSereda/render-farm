package com.example.renderfarm.server.service;

import com.example.renderfarm.server.entity.Client;
import com.example.renderfarm.server.entity.StatusHistory;
import com.example.renderfarm.server.entity.Task;
import com.example.renderfarm.server.repository.StatusHistoryRepository;
import com.example.renderfarm.server.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private StatusHistoryRepository statusHistoryRepository;

    @Override
    @Transactional
    public List<Task> findAllByClient(Client client) {
        return taskRepository.findAllByClient(client);
    }

    @Override
    @Transactional
    public List<Task> findAllByClientAndNameIgnoreCase(Client client, String name) {
        return taskRepository.findAllByClientAndNameIgnoreCase(client, name);
    }

    @Override
    @Transactional
    public List<Task> findAllByClientAndId(Client client, int id) {
        return taskRepository.findAllByClientAndId(client, id);
    }

    @Override
    @Transactional
    public List<Task> findAllByClientAndStatus(Client client, Task.Status status) {
        return taskRepository.findAllByClientAndStatus(client, status);
    }

    @Override
    @Transactional
    public Task save(Task task) {
        taskRepository.save(task);
        StatusHistory statusHistory = new StatusHistory(task.getClient(), task, task.getStatus());
        statusHistoryRepository.save(statusHistory);
        return taskRepository.save(task);
    }
}
