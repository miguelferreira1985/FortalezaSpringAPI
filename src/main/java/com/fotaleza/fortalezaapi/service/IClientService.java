package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.Client;

import java.util.List;

public interface IClientService {

    Client saveClient(Client client);
    List<Client> getAllClients();
    Client getClientById(Integer clientId);
    Client getClientByName(String clientName);
    Boolean existsByClientName(String clientName);
    Client updateClient(Client client);
    void deleteClient(Integer clientId);
}
