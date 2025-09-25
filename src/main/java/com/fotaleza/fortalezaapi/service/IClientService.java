package com.fotaleza.fortalezaapi.service;

import com.fotaleza.fortalezaapi.dto.request.ClientRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.ClientResponseDTO;

import java.util.List;

public interface IClientService {

    ClientResponseDTO createClient(ClientRequestDTO clientRequestDTO);
    ClientResponseDTO updateClient(Integer clientId, ClientRequestDTO clientRequestDTO);
    void deleteClient(Integer clientId);
    ClientResponseDTO getClientById(Integer clientId);
    List<ClientResponseDTO> getAllClients(Boolean isActivate);

}
