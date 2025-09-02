package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.ClientDTO;

import java.util.List;

public interface IClientService {

    ClientDTO createClient(ClientDTO clientDTO);
    ClientDTO updateClient(Integer clientId, ClientDTO clientDTO);
    void deleteClient(Integer clientId);
    ClientDTO getClientById(Integer clientId);
    List<ClientDTO> getAllClients(Boolean isActivate);

}
