package com.example.renderfarm.server.repository;

import com.example.renderfarm.server.entity.Client;
import com.example.renderfarm.server.entity.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Integer> {
    List<StatusHistory> findAllByClient(Client client);
}
