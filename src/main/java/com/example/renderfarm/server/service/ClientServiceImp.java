package com.example.renderfarm.server.service;

import com.example.renderfarm.server.entity.Client;
import com.example.renderfarm.server.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientServiceImp implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Transactional
    public Client findClientByLogin(String login) {
        return clientRepository.findByLogin(login);
    }

    @Override
    @Transactional
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public Client findByLogin(String login) {
        return clientRepository.findByLogin(login);
    }
}
