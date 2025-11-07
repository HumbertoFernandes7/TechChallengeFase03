package com.fiap.techchallenge.agendamento.dtos;

import com.fiap.techchallenge.agendamento.enums.StatusConsulta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoDTO implements Serializable {

    private String emailPaciente;
    private String nomePaciente;
    private String nomeMedico;
    private LocalDateTime dataHoraConsulta;
    private StatusConsulta status;
    private String mensagemNotificacao;
}