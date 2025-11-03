package com.fiap.techchallenge.agendamento.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Email
    @NotBlank(message = "Email é Obrigatório")
    private String email;

    @NotBlank(message = "Senha é Obrigatória")
    private String senha;
}
