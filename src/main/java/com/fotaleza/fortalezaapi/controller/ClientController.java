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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    @GetMapping
    @RequestMapping("/getAllClients")
    public ResponseEntity<?> getAllClients(@RequestParam("isActivate") boolean isActivate) {

        List<ClientResponseDto> clientResponseDtoList = new ArrayList<>();

        if (isActivate) {
            clientResponseDtoList = ClientMapperDto.toModelList(clientService.getAllActiveClients());
        } else {
            clientResponseDtoList = ClientMapperDto.toModelList(clientService.getAllInactiveClients());
        }

        return ResponseEntity.ok(clientResponseDtoList);
    }


    @GetMapping
    public ResponseEntity<?> getClientById(@RequestParam("clientId") Integer clientId) {

        Client client = clientService.getClientById(clientId);

        if (Objects.nonNull(client)) {

            ClientResponseDto clientResponseDto = ClientMapperDto.toModel(client);

            return ResponseEntity.ok(clientResponseDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {

        if (clientService.existsByRfc(clientRequestDto.getRfc())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Este RFC ya esta registrado para un cliente!", clientRequestDto.getRfc()));
        }

        Client newClient = ClientMapperDto.toEntity(clientRequestDto);
        newClient.setCreatedDateTime(new Date());
        newClient.setUpdatedDateTime(new Date());
        newClient.setIsActivate(true);

        clientService.saveClient(newClient);

        return ResponseEntity.ok(new MessageResponse("Cliente registrado con exito!", newClient));

    }

    @PutMapping
    public ResponseEntity<?> updateClient(@Valid @RequestBody ClientRequestDto clientRequestDto) {

        Client clientToUpdate = clientService.getClientById(clientRequestDto.getId());

        if (Objects.nonNull(clientToUpdate)) {
            if (clientService.existsByRfc(clientRequestDto.getRfc())) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse(
                                "No se puede actualizar el cliente " + clientRequestDto.getCompanyName() + " , el cliente ya se encuentra registrado.",
                                clientRequestDto));
            }

            clientToUpdate = ClientMapperDto.toEntity(clientRequestDto);
            clientToUpdate.setUpdatedDateTime(new Date());

            clientService.updateClient(clientToUpdate);

            return ResponseEntity.ok(new MessageResponse("Cliente " + clientToUpdate.getCompanyName() + " actualizado con exito.", clientToUpdate));
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("No existe el cliente " + clientRequestDto.getCompanyName() + ".", clientRequestDto));
        }

    }

    @DeleteMapping
    public ResponseEntity<?> deleteClient(@Param("clientId") Integer clientId) {

        Client clientToDelete = clientService.getClientById(clientId);

        if ( Objects.nonNull(clientToDelete) ) {
            clientService.deleteClient(clientId);
            return ResponseEntity.ok(new MessageResponse("Client eliminado exitosamente!", clientToDelete));
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("No se encontro el cliente que desea eliminar", null));
        }
    }
}
