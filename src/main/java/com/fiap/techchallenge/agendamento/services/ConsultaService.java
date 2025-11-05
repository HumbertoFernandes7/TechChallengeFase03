package com.fiap.techchallenge.agendamento.services;

import com.fiap.techchallenge.agendamento.entities.ConsultaEntity;
import com.fiap.techchallenge.agendamento.entities.UserEntity;
import com.fiap.techchallenge.agendamento.enums.StatusConsulta;
import com.fiap.techchallenge.agendamento.enums.TipoUsuario;
import com.fiap.techchallenge.agendamento.repositories.ConsultaRepository;
import com.fiap.techchallenge.agendamento.services.validations.consulta.ConsultaCreateValidation;
import com.fiap.techchallenge.agendamento.services.validations.consulta.ConsultaUpdateValidation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final UserService userService;
    private final List<ConsultaCreateValidation> validarCadastro;
    private final List<ConsultaUpdateValidation> validarUpdate;

    @Transactional
    public ConsultaEntity create(ConsultaEntity consultaEntity, UserEntity medico, UserEntity paciente){
        consultaEntity.setMedico(medico);
        consultaEntity.setPaciente(paciente);
        consultaEntity.setStatus(StatusConsulta.AGENDADA);
        validarCadastro.forEach(v -> v.valida(consultaEntity));
        return consultaRepository.save(consultaEntity);
    }

    public ConsultaEntity findById(Long id){
        return consultaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Consulta não encontrada"));
    }

    public List<ConsultaEntity> findAll(){
        UserEntity usuarioLogado = userService.getUserLogado();
        if(usuarioLogado.getTipoUsuario() != TipoUsuario.PACIENTE){
            return consultaRepository.findAll();
        }
        return consultaRepository.findAllByPaciente(usuarioLogado);
    }

    public ConsultaEntity update(ConsultaEntity consultaEntity, UserEntity medico, UserEntity paciente){
        consultaEntity.setMedico(medico);
        consultaEntity.setPaciente(paciente);
        validarUpdate.forEach(v -> v.valida(consultaEntity));
        return consultaRepository.save(consultaEntity);
    }

    public void delete(Long id){
        consultaRepository.deleteById(id);
    }

    public void realizarConsulta(ConsultaEntity consultaEncontrada) {
        consultaEncontrada.setStatus(StatusConsulta.REALIZADA);
        consultaRepository.save(consultaEncontrada);
    }
}