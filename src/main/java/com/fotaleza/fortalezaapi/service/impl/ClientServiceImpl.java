package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.ClientDTO;
import com.fotaleza.fortalezaapi.exception.ClientAlreadyExistsException;
import com.fotaleza.fortalezaapi.exception.ProductNotFoundException;
import com.fotaleza.fortalezaapi.mapper.ClientMapper;
import com.fotaleza.fortalezaapi.model.Client;
import com.fotaleza.fortalezaapi.repository.ClientRepository;
import com.fotaleza.fortalezaapi.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        if (clientRepository.findByRfc(clientDTO.getRfc()).isPresent()) {
            throw new ClientAlreadyExistsException("El cliente con el RFC ya existe.");
        }

        Client client = clientMapper.toEntity(clientDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toDTO(savedClient);
    }

    @Override
    public ClientDTO updateClient(Integer clientId, ClientDTO clientDTO) {
        Client clientToUpdate = clientRepository.findById(clientId)
                .orElseThrow(() -> new ProductNotFoundException("El cliente no existe."));

        // Check for a duplicate RFC only if the RFC is different from the original client's RFC
        if (clientDTO.getRfc() != null && !clientDTO.getRfc().equals(clientToUpdate.getRfc())) {
            clientRepository.findByRfc(clientDTO.getRfc())
                    .ifPresent(existingClient -> {
                        // This check ensures the found client is not the one we're currently updating
                        if (!existingClient.getId().equals(clientId)) {
                            throw new ClientAlreadyExistsException("El cliente con el RFC ya existe.");
                        }
                    });
        }

        clientMapper.updateEntityFromDTO(clientDTO, clientToUpdate);

        Client updatedClient = clientRepository.save(clientToUpdate);
        return clientMapper.toDTO(updatedClient);

    }

    @Override
    public void deleteClient(Integer clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ProductNotFoundException("El cliente no existe."));
        client.setIsActivate(false);
        clientRepository.save(client);
    }

    @Override
    public ClientDTO getClientById(Integer clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ProductNotFoundException("El cliente no existe."));
        return clientMapper.toDTO(client);
    }

    @Override
    public List<ClientDTO> getAllClients(Boolean isActivate) {
        List<Client> clients;

        if(isActivate != null) {
            clients = clientRepository.findByIsActivate(isActivate);
        } else {
            clients = clientRepository.findAll();
        }
        return clientMapper.toDTOList(clients);
    }

}
