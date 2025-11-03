package com.fiap.techchallenge.agendamento.dtos;

import com.fiap.techchallenge.agendamento.entities.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

    @NotBlank(message = "Nome é Obrigatório")
    private String nome;

    @Email(message = "Formato de email inválido")
    @NotBlank(message = "Email é Obrigatório")
    private String email;

    @NotNull(message = "Tipo de Usuário é Obrigatório")
    private TipoUsuario tipoUsuario;
}
