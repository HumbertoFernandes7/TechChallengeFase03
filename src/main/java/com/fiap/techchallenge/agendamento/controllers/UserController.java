package com.fiap.techchallenge.agendamento.controllers;

import com.fiap.techchallenge.agendamento.dtos.UserRequest;
import com.fiap.techchallenge.agendamento.dtos.UserResponse;
import com.fiap.techchallenge.agendamento.dtos.UserUpdateRequest;
import com.fiap.techchallenge.agendamento.entities.UserEntity;
import com.fiap.techchallenge.agendamento.mappers.UserMapper;
import com.fiap.techchallenge.agendamento.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest userRequest) {
       UserEntity usuarioEntity = userMapper.toEntity(userRequest);
       UserEntity usuarioSalvo = userService.create(usuarioEntity);
       return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toResponse(usuarioSalvo));
    }

    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@RequestBody @Valid UserUpdateRequest updateRequest, @PathVariable Long id) {
        UserEntity usuarioEncontrado = userService.findById(id);
        userMapper.copyInputToEntity(updateRequest, usuarioEncontrado);
        UserEntity usuarioSalvo = userService.update(usuarioEncontrado);
        return ResponseEntity.ok(userMapper.toResponse(usuarioSalvo));
    }

    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        UserEntity user = userService.findById(id);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserEntity> users = userService.findAll();
        List<UserResponse> userResponses = userMapper.listEntityToListResponse(users);
        return ResponseEntity.ok(userResponses);
    }

    @PreAuthorize("hasRole('MEDICO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.findById(id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}