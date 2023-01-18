package com.example.renderfarm.server.repository;

import com.example.renderfarm.server.entity.Client;
import com.example.renderfarm.server.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findAllByClient(Client client);

    List<Task> findAllByClientAndName(Client client, String name);

    List<Task> findAllByClientAndId(Client client, int id);

    List<Task> findAllByClientAndStatus(Client client, Task.Status status);

}
