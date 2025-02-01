package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.ClientRequestDto;
import com.fotaleza.fortalezaapi.dto.response.ClientResponseDto;
import com.fotaleza.fortalezaapi.dto.response.MessageResponse;
import com.fotaleza.fortalezaapi.model.Client;
import com.fotaleza.fortalezaapi.service.impl.ClientServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    @GetMapping
    @RequestMapping("/getAllActiveClients")
    public List<Client> getAllActiveClients() { return clientService.getAllActiveClients(); }

    @GetMapping
    @RequestMapping("/getAllInactiveClients")
    public List<Client> getAllInactiveClients() {
        return clientService.getAllInactiveClients();
    }

    @GetMapping
    public ResponseEntity<?> getClientById(@RequestParam("clientId") Integer clientId) {

        Client client = clientService.getClientById(clientId);

        if (Objects.nonNull(client)) {

            ClientResponseDto clientResponseDto = new ClientResponseDto();
            clientResponseDto.setId(client.getId());
            clientResponseDto.setCompanyName(client.getCompanyName());
            clientResponseDto.setFirstName(client.getFirstName());
            clientResponseDto.setLastName(client.getLastName());
            clientResponseDto.setAddress(client.getAddress());
            clientResponseDto.setEmail(client.getEmail());
            clientResponseDto.setPhone(client.getPhone());
            clientResponseDto.setRfc(client.getRfc());
            clientResponseDto.setCreatedDateTime(client.getCreatedDateTime());
            clientResponseDto.setUpdatedDateTime(client.getUpdatedDateTime());
            clientResponseDto.setIsActivate(client.getIsActivate());

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

        Client newClient = new Client();
        newClient.setCompanyName(clientRequestDto.getCompanyName());
        newClient.setFirstName(clientRequestDto.getFirstName());
        newClient.setLastName(clientRequestDto.getLastName());
        newClient.setAddress(clientRequestDto.getAddress());
        newClient.setEmail(clientRequestDto.getEmail());
        newClient.setPhone(clientRequestDto.getPhone());
        newClient.setRfc(clientRequestDto.getRfc());
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

            clientToUpdate.setCompanyName(clientRequestDto.getCompanyName());
            clientToUpdate.setFirstName(clientRequestDto.getFirstName());
            clientToUpdate.setLastName(clientRequestDto.getLastName());
            clientToUpdate.setAddress(clientRequestDto.getAddress());
            clientToUpdate.setEmail(clientRequestDto.getEmail());
            clientToUpdate.setPhone(clientRequestDto.getPhone());
            clientToUpdate.setRfc(clientRequestDto.getRfc());
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
            if (!clientToDelete.getIsActivate()) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse(
                                "El cliente ya estaba eliminado!",
                                clientToDelete));
            }

            clientService.deleteClient(clientId);

            return ResponseEntity.ok(new MessageResponse("Client eliminado exitosamente!", clientToDelete));
        } else {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("No se encontro el cliente que desea eliminar", null));
        }
        
    }

}
