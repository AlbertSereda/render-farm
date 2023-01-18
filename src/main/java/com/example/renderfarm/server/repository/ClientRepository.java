package com.example.renderfarm.server.repository;

import com.example.renderfarm.server.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByLogin(String login);
}
