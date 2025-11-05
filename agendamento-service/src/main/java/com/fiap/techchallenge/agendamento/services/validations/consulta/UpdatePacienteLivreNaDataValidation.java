package com.fiap.techchallenge.agendamento.services.validations.consulta;

import com.fiap.techchallenge.agendamento.entities.ConsultaEntity;
import com.fiap.techchallenge.agendamento.exception.BadRequestBusinessException;
import com.fiap.techchallenge.agendamento.repositories.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UpdatePacienteLivreNaDataValidation implements ConsultaUpdateValidation {

    private final ConsultaRepository consultaRepository;

    @Override
    public void valida(ConsultaEntity consulta) {
        LocalDateTime dataConsulta = consulta.getDataConsulta();
        Optional<ConsultaEntity> consultaEncontrada = consultaRepository.findByPacienteAndDataConsulta(consulta.getPaciente(), dataConsulta);
        if(consultaEncontrada.isPresent() &&
                !consultaEncontrada.get().getId().equals(consulta.getId())){
            throw new BadRequestBusinessException("Já existe uma consulta marcada para este horário e paciente");
        }
    }
}