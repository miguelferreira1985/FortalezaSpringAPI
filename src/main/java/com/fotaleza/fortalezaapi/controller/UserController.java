package com.fotaleza.fortalezaapi.controller;

import com.fotaleza.fortalezaapi.dto.request.ChangePasswordRequestDTO;
import com.fotaleza.fortalezaapi.dto.request.UpdateRolesRequestDTO;
import com.fotaleza.fortalezaapi.dto.response.ApiResponse;
import com.fotaleza.fortalezaapi.dto.response.UserResponseDTO;
import com.fotaleza.fortalezaapi.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints para gestión de usuarios en el sistema de Inventario")
public class UserController {

    private final IUserService userService;

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<UserResponseDTO>> activateUser(@PathVariable Long id) {
        UserResponseDTO activateUser = userService.activateUser(id);

        return ResponseEntity.ok(
                ApiResponse.<UserResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Usuario activado existosamente.")
                        .data(activateUser)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<UserResponseDTO>> deactivateUser(@PathVariable Long id) {
        UserResponseDTO deactivateUser = userService.deactivateUser(id);

        return ResponseEntity.ok(
                ApiResponse.<UserResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Usuario desactivado existosamente.")
                        .data(deactivateUser)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PatchMapping("/{id}/unblock")
    public ResponseEntity<ApiResponse<UserResponseDTO>> unblockUser(@PathVariable Long id) {
        UserResponseDTO deactivateUser = userService.unblockUser(id);

        return ResponseEntity.ok(
                ApiResponse.<UserResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Usuario desbloqueado existosamente.")
                        .data(deactivateUser)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<ApiResponse<UserResponseDTO>> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO) {

        UserResponseDTO updatedUser = userService.changePassowrd(id, changePasswordRequestDTO.getNewPassword());

        return ResponseEntity.ok(
                ApiResponse.<UserResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("La contraseña ha sido actualizada existosamente.")
                        .data(updatedUser)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PatchMapping("/{id}/roles")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUserRoles(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRolesRequestDTO updateRolesRequestDTO) {

        UserResponseDTO updatedUser = userService.updateRoles(id, updateRolesRequestDTO.getRoles());

        return ResponseEntity.ok(
                ApiResponse.<UserResponseDTO>builder()
                        .status(HttpStatus.OK.value())
                        .message("Roles del usuario actualizados exitosamente.")
                        .data(updatedUser)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<ApiResponse<Void>> deletePresentation(@PathVariable Long id) {

        userService.deleteUserIfDeactivate(id);

        return  ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .status(HttpStatus.OK.value())
                        .message("El usuario fue eliminad.")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers(
            @RequestParam(name = "isActivate", required = false) Boolean isActivate) {
        List<UserResponseDTO> users = userService.getAllUsers(isActivate);

        return ResponseEntity.ok(
                ApiResponse.<List<UserResponseDTO>>builder()
                        .status(HttpStatus.OK.value())
                        .message("Usuarios obtenidos existosamente.")
                        .data(users)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

}
