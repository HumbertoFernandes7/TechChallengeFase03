package com.fiap.techchallenge.agendamento.services;

import com.fiap.techchallenge.agendamento.dtos.NotificacaoDTO;
import com.fiap.techchallenge.agendamento.entities.ConsultaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class NotificationSenderService {

    public static final String CONSULTA_QUEUE_NAME = "fila.consultas.notificacao";
    private static final Logger logger = LoggerFactory.getLogger(NotificationSenderService.class);

    private final RabbitTemplate rabbitTemplate;

    @Async
    public void notifica(ConsultaEntity consulta) {
        try {
            NotificacaoDTO notificacao = new NotificacaoDTO(
                    consulta.getPaciente().getEmail(),
                    consulta.getPaciente().getNome(),
                    consulta.getMedico().getNome(),
                    consulta.getDataConsulta(),
                    consulta.getStatus()
            );
            // Envia a mensagem para a fila
            rabbitTemplate.convertAndSend(CONSULTA_QUEUE_NAME, notificacao);
        } catch (Exception e) {
            logger.error("Falha ao enviar notificação de agendamento: " + e.getMessage());
        }
    }
}
