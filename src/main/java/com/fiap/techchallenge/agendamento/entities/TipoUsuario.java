package com.fiap.techchallenge.agendamento.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TipoUsuario {

    MEDICO("medico"), ENFERMEIRO("enfermeiro"), PACIENTE("paciente");

    private String tipo;

}