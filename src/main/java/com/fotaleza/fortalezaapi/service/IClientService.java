package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.model.Client;

import java.util.List;
import java.util.Optional;

public interface IClientService {

    Client saveClient(Client client);
    List<Client> getAllClients();
    Client getClientById(Integer clientId);
    Optional<Client> getClientByName(String clientName);
    Boolean existsByClientName(String clientName);
    Client updateClient(Client client);
    void deleteClient(Integer clientId);
}
