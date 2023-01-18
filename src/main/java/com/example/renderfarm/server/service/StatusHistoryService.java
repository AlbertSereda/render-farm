package com.example.renderfarm.server.service;

import com.example.renderfarm.server.entity.Client;
import com.example.renderfarm.server.entity.StatusHistory;

import java.util.List;

public interface StatusHistoryService {
    List<StatusHistory> findAllByClient(Client client);
}
