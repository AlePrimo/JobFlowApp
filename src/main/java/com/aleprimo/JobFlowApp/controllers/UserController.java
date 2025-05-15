package com.aleprimo.JobFlowApp.controllers;

import com.aleprimo.JobFlowApp.controllers.dtos.UserEntityDTO;
import com.aleprimo.JobFlowApp.models.UserEntity;
import com.aleprimo.JobFlowApp.services.IUserEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {




    private final IUserEntityService userService;

    public UserController(IUserEntityService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<UserEntityDTO>> getAllUsers() {
        List<UserEntityDTO> userEntityDTOS = this.userService.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();

        return ResponseEntity.ok(userEntityDTOS);
    }


    @GetMapping("/findById/{id}")
    public ResponseEntity<UserEntityDTO> getUserById(@PathVariable Long id) {
        return this.userService.findById(id)
                .map(user -> ResponseEntity.ok(mapToDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/saveUser")
    public ResponseEntity<UserEntityDTO> createUser(@RequestBody UserEntityDTO userDTO) {
        UserEntity newUser = this.userService.save(mapToEntity(userDTO));
        return ResponseEntity.ok(mapToDTO(newUser));
    }


    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserEntityDTO> updateUser(@PathVariable Long id, @RequestBody UserEntityDTO userDTO) {
        UserEntity user = mapToEntity(userDTO);
        user.setId(id);
        this.userService.save(user);
        return ResponseEntity.ok(mapToDTO(user));
    }


    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable Long id) {
       this.userService.deleteById(id);
    }



    private UserEntityDTO mapToDTO(UserEntity user) {
        UserEntityDTO dto = new UserEntityDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setBirthDate(user.getBirthDate());
        dto.setCvUrl(user.getCvUrl());
        return dto;
    }

    private UserEntity mapToEntity(UserEntityDTO dto) {
        UserEntity user = new UserEntity();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());
        user.setCvUrl(dto.getCvUrl());
        return user;
    }

}
