package com.example.renderfarm.server.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "StatusHistory")
public class StatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "idClient")
    private Client client;

    @ManyToOne()
    @JoinColumn(name = "idTask")
    private Task task;

    private Task.Status status;

    private Date date;

    public StatusHistory() {
    }

    public StatusHistory(Client client, Task task, Task.Status status) {
        this.client = client;
        this.task = task;
        this.status = status;
        date = new Date();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Task.Status getStatus() {
        return status;
    }

    public void setStatus(Task.Status status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
