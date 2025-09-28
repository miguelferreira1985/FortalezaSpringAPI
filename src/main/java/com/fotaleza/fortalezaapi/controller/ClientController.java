package com.fotaleza.fortalezaapi.controller;


import com.fotaleza.fortalezaapi.dto.request.ClientRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.ApiResponse;
import com.fotaleza.fortalezaapi.dto.response.ClientResponseDTO;
import com.fotaleza.fortalezaapi.service.IClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Endpoints para gestión de clientes en el sistema CRM")
public class ClientController {

    private final IClientService clientService;

    @PostMapping
    public ResponseEntity<ApiResponse<ClientResponseDTO>> createClient(@Valid @RequestBody ClientRequestDTO clientRequestDTO) {

        ClientResponseDTO createdClient = clientService.createClient(clientRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<ClientResponseDTO>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Cliente creado existosamente.")
                        .data(createdClient)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientResponseDTO>> updateClient(@PathVariable Integer id, @Valid @RequestBody ClientRequestDTO clientRequestDTO) {

        ClientResponseDTO updatedClient = clientService.updateClient(id, clientRequestDTO);

        return ResponseEntity.ok(
                ApiResponse.<ClientResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Cliente actualizado existosamente.")
                        .data(updatedClient)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable Integer id) {

        clientService.deleteClient(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("Cliente eliminado existosamente.")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClientResponseDTO>> getClientById(@PathVariable Integer id) {

        ClientResponseDTO client = clientService.getClientById(id);

        return ResponseEntity.ok(
                ApiResponse.<ClientResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Cliente obtenido existosamente.")
                        .data(client)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClientResponseDTO>>> getAllClients(@RequestParam(name = "isActivate", required = false) Boolean isActivate) {

        List<ClientResponseDTO> clients = clientService.getAllClients(isActivate);

        return ResponseEntity.ok(
                ApiResponse.<List<ClientResponseDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Clientes obtenidos existosamente.")
                        .data(clients)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}
