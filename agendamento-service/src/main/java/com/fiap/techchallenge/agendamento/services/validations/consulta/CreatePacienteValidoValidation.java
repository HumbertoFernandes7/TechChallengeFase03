package com.fiap.techchallenge.agendamento.services.validations.consulta;

import com.fiap.techchallenge.agendamento.entities.ConsultaEntity;
import com.fiap.techchallenge.agendamento.enums.TipoUsuario;
import com.fiap.techchallenge.agendamento.exception.BadRequestBusinessException;
import org.springframework.stereotype.Component;

@Component
public class CreatePacienteValidoValidation implements ConsultaCreateValidation {

    @Override
    public void valida(ConsultaEntity consulta) {
        if (consulta.getPaciente().getTipoUsuario() != TipoUsuario.PACIENTE) {
            throw new BadRequestBusinessException("O usuário não é um paciente");
        }
    }
}