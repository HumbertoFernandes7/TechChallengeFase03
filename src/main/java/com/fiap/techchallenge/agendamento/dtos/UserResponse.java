package com.fiap.techchallenge.agendamento.dtos;

import com.fiap.techchallenge.agendamento.enums.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;

    private String nome;

    private String email;

    private TipoUsuario tipoUsuario;

}
