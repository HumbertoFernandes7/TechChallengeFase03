package com.fiap.techchallenge.agendamento.services.validations.consulta;

import com.fiap.techchallenge.agendamento.entities.ConsultaEntity;
import com.fiap.techchallenge.agendamento.enums.TipoUsuario;
import org.springframework.stereotype.Component;

@Component
public class CreateMedicoValidoValidation implements ConsultaCreateValidation {

    @Override
    public void valida(ConsultaEntity consulta) {
        if (consulta.getMedico().getTipoUsuario() != TipoUsuario.MEDICO){
            throw new RuntimeException("O usuário não é um médico");
        }
    }
}