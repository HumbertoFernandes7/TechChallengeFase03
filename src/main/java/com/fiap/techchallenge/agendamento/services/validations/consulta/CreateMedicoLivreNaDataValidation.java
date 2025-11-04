package com.fiap.techchallenge.agendamento.services.validations.consulta;

import com.fiap.techchallenge.agendamento.entities.ConsultaEntity;
import com.fiap.techchallenge.agendamento.repositories.ConsultaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CreateMedicoLivreNaDataValidation implements ConsultaCreateValidation {

    private final ConsultaRepository consultaRepository;

    @Override
    public void valida(ConsultaEntity consulta) {
        if (consultaRepository.findByMedicoAndDataConsulta(consulta.getMedico(), consulta.getDataConsulta()).isPresent()){
            throw new RuntimeException("Já existe uma consulta marcada para este horário e médico");
        }
    }
}