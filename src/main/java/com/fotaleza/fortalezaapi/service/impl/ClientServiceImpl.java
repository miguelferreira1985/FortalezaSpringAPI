package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.model.Client;
import com.fotaleza.fortalezaapi.repository.ClientRepository;
import com.fotaleza.fortalezaapi.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll().stream().toList();
    }

    @Override
    public Client getClientById(Integer clientId) {
        return clientRepository.findById(clientId).orElse(null);
    }

    @Override
    public Boolean existsByRfc(String rfc) {
        return clientRepository.existsByRfc(rfc);
    }

    @Override
    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteClient(Integer clientId) {
        clientRepository.deleteById(clientId);
    }
}
