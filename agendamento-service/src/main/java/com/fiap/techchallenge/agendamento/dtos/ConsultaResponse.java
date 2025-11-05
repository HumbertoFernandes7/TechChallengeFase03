package com.fiap.techchallenge.agendamento.dtos;

import com.fiap.techchallenge.agendamento.enums.StatusConsulta;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConsultaResponse {

    private Long id;

    private UserResponse medico;

    private UserResponse paciente;

    private LocalDateTime dataConsulta;

    private StatusConsulta status;

}