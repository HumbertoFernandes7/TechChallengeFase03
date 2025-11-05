package com.fiap.techchallenge.agendamento.controllers;

import com.fiap.techchallenge.agendamento.dtos.LoginRequest;
import com.fiap.techchallenge.agendamento.dtos.LoginResponse;
import com.fiap.techchallenge.agendamento.entities.UserEntity;
import com.fiap.techchallenge.agendamento.exception.InvalidCredentialsBusinessException;
import com.fiap.techchallenge.agendamento.services.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getSenha()
            );
            Authentication auth = authenticationManager.authenticate(usernamePassword);

            UserEntity user = (UserEntity) auth.getPrincipal();

            String token = tokenService.generateToken(user);

            return ResponseEntity.ok(new LoginResponse(token));

        } catch (Exception e) {
            throw new InvalidCredentialsBusinessException("Email ou senha inválidos!");
        }
    }
}