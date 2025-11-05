package com.fiap.techchallenge.agendamento.services.validations.consulta;

import com.fiap.techchallenge.agendamento.entities.ConsultaEntity;
import com.fiap.techchallenge.agendamento.enums.TipoUsuario;
import com.fiap.techchallenge.agendamento.exception.BadRequestBusinessException;
import org.springframework.stereotype.Component;

@Component
public class CreateMedicoValidoValidation implements ConsultaCreateValidation {

    @Override
    public void valida(ConsultaEntity consulta) {
        if (consulta.getMedico().getTipoUsuario() != TipoUsuario.MEDICO){
            throw new BadRequestBusinessException("O usuário não é um médico");
        }
    }
}