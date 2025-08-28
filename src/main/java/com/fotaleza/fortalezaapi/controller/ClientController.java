package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.ClientRequestDto;
import com.fotaleza.fortalezaapi.dto.response.ClientResponseDto;
import com.fotaleza.fortalezaapi.dto.response.MessageResponse;
import com.fotaleza.fortalezaapi.mapper.ClientMapperDto;
import com.fotaleza.fortalezaapi.model.Client;
import com.fotaleza.fortalezaapi.service.impl.ClientServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientServiceImpl clientService;

    @Autowired
    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @RequestMapping("/getAllClients")
    public ResponseEntity<?> getAllClients(@RequestParam("isActivate") boolean isActivate) {

        List<ClientResponseDto> clientResponseDtoList = ClientMapperDto.toModelList(clientService.getAllClients(isActivate));

        if (clientResponseDtoList.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Clientes no encontrado", null));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clientResponseDtoList);
    }

    @GetMapping
    public ResponseEntity<?> getClientById(@RequestParam("clientId") Integer clientId) {

        Client client = clientService.getClientById(clientId);

        if (Objects.nonNull(client)) {

            ClientResponseDto clientResponseDto = ClientMapperDto.toModel(client);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(clientResponseDto);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("El cliente no fue encontrado.", null));
        }
    }

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {

        if (clientService.existsByRfc(clientRequestDto.getRfc())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse(
                            String.format("El RFC %s ya esta registrado para un cliente!", clientRequestDto.getRfc()), clientRequestDto));
        }

        Client newClient = ClientMapperDto.toEntity(clientRequestDto);
        newClient.setCreatedDateTime(new Date());
        newClient.setUpdatedDateTime(new Date());
        newClient.setIsActivate(true);

        clientService.saveClient(newClient);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MessageResponse("Cliente registrado con exito!", newClient));

    }

    @PutMapping
    public ResponseEntity<?> updateClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {

        Client clientToUpdate = clientService.getClientById(clientRequestDto.getId());

        if (Objects.nonNull(clientToUpdate)) {
            if (clientService.existsByRfc(clientRequestDto.getRfc())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse(
                                String.format("No se puede actualizar el cliente %s, el cliente ya se encuentra registrado.", clientRequestDto.getCompanyName()),
                                clientRequestDto));
            }

            clientToUpdate = ClientMapperDto.toEntity(clientRequestDto);
            clientToUpdate.setUpdatedDateTime(new Date());

            clientService.updateClient(clientToUpdate);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new MessageResponse(
                            String.format("Cliente %s actualizado con exito.", clientToUpdate.getCompanyName()), clientToUpdate));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(
                            String.format("No existe el cliente %s.", clientRequestDto.getCompanyName()), null));
        }

    }

    @DeleteMapping
    public ResponseEntity<?> deleteClient(@Param("clientId") Integer clientId) {

        Client clientToDelete = clientService.getClientById(clientId);

        if (!Objects.nonNull(clientToDelete) ) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("No se encontro el cliente que desea eliminar", null));
        }

        clientService.deleteClient(clientId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Client eliminado exitosamente!", clientToDelete));
    }
}
