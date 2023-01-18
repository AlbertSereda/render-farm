package com.example.renderfarm.server.service;

import com.example.renderfarm.server.entity.Client;
import com.example.renderfarm.server.entity.StatusHistory;
import com.example.renderfarm.server.repository.StatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StatusHistoryServiceImpl implements StatusHistoryService {

    @Autowired
    private StatusHistoryRepository statusHistoryRepository;

    @Override
    @Transactional
    public List<StatusHistory> findAllByClient(Client client) {
        return statusHistoryRepository.findAllByClient(client);
    }
}
