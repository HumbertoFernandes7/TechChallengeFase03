package com.fiap.techchallenge.agendamento.dtos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConsultaRequest {

    @NotNull(message = "O ID do médico é obrigatório")
    private Long idMedico;

    @NotNull(message = "O ID do paciente é obrigatório")
    private Long idPaciente;

    @NotNull(message = "A data e hora são obrigatórias")
    @Future(message = "A data da consulta deve ser no futuro")
    private LocalDateTime dataConsulta;

}
