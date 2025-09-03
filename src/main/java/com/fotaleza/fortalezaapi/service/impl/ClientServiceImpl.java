package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.ClientRequestDTO;
import com.fotaleza.fortalezaapi.dto.ClientResponseDTO;
import com.fotaleza.fortalezaapi.exception.ResourceAlreadyExistsException;
import com.fotaleza.fortalezaapi.exception.ResourceNotFoundException;
import com.fotaleza.fortalezaapi.mapper.ClientMapper;
import com.fotaleza.fortalezaapi.model.Client;
import com.fotaleza.fortalezaapi.repository.ClientRepository;
import com.fotaleza.fortalezaapi.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional
    public ClientResponseDTO createClient(ClientRequestDTO clientRequestDTO) {
        validateRfcUnique(clientRequestDTO.getRfc(), null);

        Client client = clientMapper.toEntity(clientRequestDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toResponseDTO(savedClient);
    }

    @Override
    @Transactional
    public ClientResponseDTO updateClient(Integer clientId, ClientRequestDTO clientRequestDTO) {
        Client clientToUpdate = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("El cliente no existe."));

        validateRfcUnique(clientRequestDTO.getRfc(), clientId);

        clientMapper.updateEntityFromRequestDTO(clientRequestDTO, clientToUpdate);

        Client updatedClient = clientRepository.save(clientToUpdate);
        return clientMapper.toResponseDTO(updatedClient);

    }

    @Override
    @Transactional
    public void deleteClient(Integer clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("El cliente no existe."));

        client.setIsActivate(false);
        clientRepository.save(client);
    }

    @Override
    public ClientResponseDTO getClientById(Integer clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("El cliente no existe."));
        return clientMapper.toResponseDTO(client);
    }

    @Override
    public List<ClientResponseDTO> getAllClients(Boolean isActivate) {
        List<Client> clients = Optional.ofNullable(isActivate)
                .map(clientRepository::findByIsActivate)
                .orElseGet(clientRepository::findAll);
        return clientMapper.toResponseDTOList(clients);
    }

    private void validateRfcUnique(String rfc, Integer clientId) {
        clientRepository.findByRfc(rfc)
                .ifPresent(c -> {
                    if (clientId == null || !c.getId().equals(clientId)) {
                        throw new ResourceAlreadyExistsException("El cliente con RFC ya existe.");
                    }
                });
    }

}
