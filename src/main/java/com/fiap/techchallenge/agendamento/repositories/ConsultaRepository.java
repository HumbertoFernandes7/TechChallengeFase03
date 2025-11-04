package com.fiap.techchallenge.agendamento.repositories;

import com.fiap.techchallenge.agendamento.entities.ConsultaEntity;
import com.fiap.techchallenge.agendamento.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConsultaRepository extends JpaRepository<ConsultaEntity, Long> {

    Optional<ConsultaEntity> findByMedicoAndDataConsulta(UserEntity medico, LocalDateTime dataConsulta);
    Optional<ConsultaEntity> findByPacienteAndDataConsulta(UserEntity paciente, LocalDateTime dataConsulta);
}
