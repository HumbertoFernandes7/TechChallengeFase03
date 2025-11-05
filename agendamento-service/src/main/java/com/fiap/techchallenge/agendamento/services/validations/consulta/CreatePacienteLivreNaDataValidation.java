package com.fiap.techchallenge.agendamento.services.validations.consulta;

import com.fiap.techchallenge.agendamento.entities.ConsultaEntity;
import com.fiap.techchallenge.agendamento.exception.BadRequestBusinessException;
import com.fiap.techchallenge.agendamento.repositories.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatePacienteLivreNaDataValidation implements ConsultaCreateValidation {

    private final ConsultaRepository consultaRepository;

    @Override
    public void valida(ConsultaEntity consulta) {
        if (consultaRepository.findByPacienteAndDataConsulta(consulta.getPaciente(), consulta.getDataConsulta()).isPresent()){
            throw new BadRequestBusinessException("Já existe uma consulta marcada para este horário e paciente");
        }
    }
}