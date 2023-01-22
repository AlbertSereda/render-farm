package com.example.renderfarm.server.service;

import com.example.renderfarm.server.entity.Client;

public interface ClientService {

    Client findClientByLoginIgnoreCase(String login);

    Client saveClient(Client client);

    Client findByLoginIgnoreCase(String s);
}
