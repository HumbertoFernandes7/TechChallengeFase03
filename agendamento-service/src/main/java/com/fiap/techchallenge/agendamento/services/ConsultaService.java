package com.fiap.techchallenge.agendamento.services;

import com.fiap.techchallenge.agendamento.entities.ConsultaEntity;
import com.fiap.techchallenge.agendamento.entities.UserEntity;
import com.fiap.techchallenge.agendamento.enums.StatusConsulta;
import com.fiap.techchallenge.agendamento.enums.TipoUsuario;
import com.fiap.techchallenge.agendamento.exception.NotFoundBusinessException;
import com.fiap.techchallenge.agendamento.exception.UnauthorizedAccessBusinessException;
import com.fiap.techchallenge.agendamento.repositories.ConsultaRepository;
import com.fiap.techchallenge.agendamento.services.validations.consulta.ConsultaCreateValidation;
import com.fiap.techchallenge.agendamento.services.validations.consulta.ConsultaUpdateValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final UserService userService;
    private final List<ConsultaCreateValidation> validarCadastro;
    private final List<ConsultaUpdateValidation> validarUpdate;

    private final NotificationSenderService notificationService;

    @Transactional
    public ConsultaEntity create(ConsultaEntity consultaEntity, UserEntity medico, UserEntity paciente) {
        consultaEntity.setMedico(medico);
        consultaEntity.setPaciente(paciente);
        consultaEntity.setStatus(StatusConsulta.AGENDADA);
        validarCadastro.forEach(v -> v.valida(consultaEntity));
        ConsultaEntity consultaSalva = consultaRepository.save(consultaEntity);
        String mensagem = "Uma nova consulta foi agendada!";
        notificationService.notifica(consultaSalva, mensagem);
        return consultaSalva;
    }

    public ConsultaEntity findById(Long id) {
        UserEntity usuarioLogado = userService.getUserLogado();

        ConsultaEntity consultaEncontrada = consultaRepository.findById(id)
                .orElseThrow(() -> new NotFoundBusinessException("Consulta não encontrada"));

        if (usuarioLogado.getTipoUsuario().equals(TipoUsuario.PACIENTE) &&
                !consultaEncontrada.getPaciente().getId().equals(usuarioLogado.getId())) {
            throw new UnauthorizedAccessBusinessException("Você não tem permissão para acessar essa consulta");
        }
        return consultaEncontrada;
    }

    public List<ConsultaEntity> findAll() {
        return findAll(null);
    }

    public List<ConsultaEntity> findAll(Boolean futuras) {
        UserEntity usuarioLogado = userService.getUserLogado();
        LocalDateTime agora = LocalDateTime.now();

        // 1. Filtro nulo (retorna todas)
        if (futuras == null) {
            if (usuarioLogado.getTipoUsuario() != TipoUsuario.PACIENTE) {
                return consultaRepository.findAll();
            } else {
                return consultaRepository.findAllByPaciente(usuarioLogado);
            }
        }

        // 2. Filtro de Futuras (futuras: true)
        if (futuras) {
            if (usuarioLogado.getTipoUsuario() != TipoUsuario.PACIENTE) {
                return consultaRepository.findAllByDataConsultaAfter(agora);
            } else {
                return consultaRepository.findAllByPacienteAndDataConsultaAfter(usuarioLogado, agora);
            }
        }
        // 3. Filtro de Passadas (futuras: false)
        else {
            if (usuarioLogado.getTipoUsuario() != TipoUsuario.PACIENTE) {
                return consultaRepository.findAllByDataConsultaBefore(agora);
            } else {
                return consultaRepository.findAllByPacienteAndDataConsultaBefore(usuarioLogado, agora);
            }
        }
    }

    public ConsultaEntity update(ConsultaEntity consultaEntity, UserEntity medico, UserEntity paciente) {
        consultaEntity.setMedico(medico);
        consultaEntity.setPaciente(paciente);
        validarUpdate.forEach(v -> v.valida(consultaEntity));
        ConsultaEntity consultaSalva = consultaRepository.save(consultaEntity);
        String mensagem = "Uma consulta foi alterada!";
        notificationService.notifica(consultaSalva, mensagem);
        return consultaSalva;
    }

    public void delete(Long id){
        consultaRepository.deleteById(id);
    }

    public void realizarConsulta(ConsultaEntity consultaEncontrada) {
        consultaEncontrada.setStatus(StatusConsulta.REALIZADA);
        consultaRepository.save(consultaEncontrada);
    }
}