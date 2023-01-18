package com.example.renderfarm.server.service;

import com.example.renderfarm.server.entity.Client;

public interface ClientService {

    Client findClientByLogin(String login);

    Client saveClient(Client client);

    Client findByLogin(String s);
}
