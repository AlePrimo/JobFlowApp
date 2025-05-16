package com.aleprimo.JobFlowApp.controllers;

import com.aleprimo.JobFlowApp.controllers.dtos.UserEntityDTO;
import com.aleprimo.JobFlowApp.models.Application;
import com.aleprimo.JobFlowApp.models.UserEntity;
import com.aleprimo.JobFlowApp.services.IApplicationService;
import com.aleprimo.JobFlowApp.services.IUserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/user")
@Tag(name = "Usuarios", description = "Manejo del CRUD de usuarios")
public class UserController {




    private final IUserEntityService userService;
    private final IApplicationService applicationService;

    public UserController(IUserEntityService userService, IApplicationService applicationService) {
        this.userService = userService;
        this.applicationService = applicationService;
    }

    @Operation(summary = "Busqueda de todos los usuarios disponibles")
    @GetMapping
    public ResponseEntity<List<UserEntityDTO>> getAllUsers() {
        List<UserEntityDTO> userEntityDTOS = this.userService.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();

        return ResponseEntity.ok(userEntityDTOS);
    }

    @Operation(summary = "Busqueda de un usuario por su ID")
    @GetMapping("/findById/{id}")
    public ResponseEntity<UserEntityDTO> getUserById(@PathVariable Long id) {
        return this.userService.findById(id)
                .map(user -> ResponseEntity.ok(mapToDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Creacion de un nuevo usuario")
    @PostMapping("/saveUser")
    public ResponseEntity<UserEntityDTO> createUser( @Valid @RequestBody UserEntityDTO userDTO) {
        UserEntity newUser = this.userService.save(mapToEntity(userDTO));
        return ResponseEntity.ok(mapToDTO(newUser));
    }

    @Operation(summary = "Actualizacion  de un usuario")
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserEntityDTO> updateUser(@Valid @PathVariable Long id, @RequestBody UserEntityDTO userDTO) {
        UserEntity user = mapToEntity(userDTO);
        user.setId(id);
        this.userService.save(user);
        return ResponseEntity.ok(mapToDTO(user));
    }

    @Operation(summary = "Eliminacion  de un usuario por su ID")
    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable Long id) {
       this.userService.deleteById(id);
    }



    private UserEntityDTO mapToDTO(UserEntity user) {
        List<Long> appIds = user.getApplications() == null ? List.of() :
                user.getApplications().stream().map(Application::getId).toList();

        UserEntityDTO dto = new UserEntityDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setBirthDate(user.getBirthDate());
        dto.setCvUrl(user.getCvUrl());
        dto.setApplicationsIds(appIds);
        dto.setRole(user.getRole());
        return dto;
    }

    private UserEntity mapToEntity(UserEntityDTO dto) {
        UserEntity user = new UserEntity();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());
        user.setCvUrl(dto.getCvUrl());
        user.setRole(dto.getRole());
        if (dto.getApplicationsIds() != null && !dto.getApplicationsIds().isEmpty()) {
            List<Application> applications = new ArrayList<>();
            for (Long appId : dto.getApplicationsIds()) {
                this.applicationService.findById(appId).ifPresent(applications::add);
            }
            user.setApplications(applications);
        }


        return user;
    }

}
