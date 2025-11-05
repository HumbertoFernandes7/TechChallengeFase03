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
        notificationService.notifica(consultaSalva);
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
        UserEntity usuarioLogado = userService.getUserLogado();
        if (usuarioLogado.getTipoUsuario() != TipoUsuario.PACIENTE) {
            return consultaRepository.findAll();
        }
        return consultaRepository.findAllByPaciente(usuarioLogado);
    }

    public ConsultaEntity update(ConsultaEntity consultaEntity, UserEntity medico, UserEntity paciente) {
        consultaEntity.setMedico(medico);
        consultaEntity.setPaciente(paciente);
        validarUpdate.forEach(v -> v.valida(consultaEntity));
        ConsultaEntity consultaSalva = consultaRepository.save(consultaEntity);
        notificationService.notifica(consultaSalva);
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