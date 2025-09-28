package com.fotaleza.fortalezaapi.service.impl;

import com.fotaleza.fortalezaapi.dto.request.ClientRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.ClientResponseDTO;
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

        validateNameAndRfcUnique(clientRequestDTO.getName(), clientRequestDTO.getRfc(), null);

        Client client = clientMapper.toEntity(clientRequestDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toResponseDTO(savedClient);
    }

    @Override
    @Transactional
    public ClientResponseDTO updateClient(Integer clientId, ClientRequestDTO clientRequestDTO) {

        Client clientToUpdate = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("El cliente que desea actualizar no existe."));

        validateNameAndRfcUnique(clientRequestDTO.getName(), clientRequestDTO.getRfc(), clientId);

        clientMapper.updateEntityFromRequestDTO(clientRequestDTO, clientToUpdate);

        Client updatedClient = clientRepository.save(clientToUpdate);
        return clientMapper.toResponseDTO(updatedClient);

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

    @Override
    public ClientResponseDTO deactivateClient(Integer clientId) {

        Client clientToDeactivate = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("El cliente no existe."));
        clientToDeactivate.setIsActivate(false);

        Client clientDeactivated = clientRepository.save(clientToDeactivate);

        return clientMapper.toResponseDTO(clientDeactivated);
    }

    @Override
    public ClientResponseDTO activateClient(Integer clientId) {

        Client clientToActivate = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("El cliente no existe."));
        clientToActivate.setIsActivate(false);

        Client clientActivated = clientRepository.save(clientToActivate);

        return clientMapper.toResponseDTO(clientActivated);
    }

    private void validateNameAndRfcUnique(String name, String rfc, Integer clientId) {

        final String errorMessage = String.format("Ya existe un cliente con el nombre %s o rfc %s", name.toUpperCase(), rfc.toUpperCase());

        if (clientId == null) {
            clientRepository.findByNameOrRfc(name, rfc)
                    .ifPresent(c -> {
                        throw new ResourceAlreadyExistsException(errorMessage);
                    });
        } else {
            clientRepository.findByNameOrRfcAndIdNot(name, rfc, clientId)
                    .ifPresent(c -> {
                        throw new ResourceAlreadyExistsException(errorMessage);
                    });
        }
    }

}
