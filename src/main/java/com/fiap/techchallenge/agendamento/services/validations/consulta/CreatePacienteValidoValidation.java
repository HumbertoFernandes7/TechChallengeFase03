package com.fiap.techchallenge.agendamento.services.validations.consulta;

import com.fiap.techchallenge.agendamento.entities.ConsultaEntity;
import com.fiap.techchallenge.agendamento.enums.TipoUsuario;
import org.springframework.stereotype.Component;

@Component
public class CreatePacienteValidoValidation implements ConsultaCreateValidation {

    @Override
    public void valida(ConsultaEntity consulta) {
        if (consulta.getPaciente().getTipoUsuario() != TipoUsuario.PACIENTE) {
            throw new RuntimeException("O usuário não é um paciente");
        }
    }
}