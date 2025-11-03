package com.fiap.techchallenge.agendamento.dtos;

import com.fiap.techchallenge.agendamento.entities.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "Nome é Obrigatório")
    private String nome;

    @Email(message = "Formato de email inválido")
    @NotBlank(message = "Email é Obrigatório")
    private String email;

    @NotBlank(message = "Senha é Obrigatória")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String senha;

    @NotNull(message = "Tipo de Usuário é Obrigatório")
    private TipoUsuario tipoUsuario;
}