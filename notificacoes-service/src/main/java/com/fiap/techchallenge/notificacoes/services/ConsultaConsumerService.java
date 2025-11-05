package com.fiap.techchallenge.notificacoes.services;

import com.fiap.techchallenge.notificacoes.dtos.NotificacaoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsultaConsumerService {

    public static final String CONSULTA_QUEUE_NAME = "fila.consultas.notificacao";

    private static final Logger logger = LoggerFactory.getLogger(ConsultaConsumerService.class);

    @RabbitListener(queues = CONSULTA_QUEUE_NAME)
    public void consumirMensagem(NotificacaoDTO notificacao) {
        logger.info("-------------------------------------------");
        logger.info("Nova notificação de consulta recebida!");
        logger.info("Status: {}", notificacao.getStatus());
        logger.info("Enviando lembrete para: {}", notificacao.getEmailPaciente());
        logger.info("Paciente: {}", notificacao.getNomePaciente());
        logger.info("Médico: {}", notificacao.getNomeMedico());
        logger.info("Data: {}", notificacao.getDataHoraConsulta());
        logger.info("-------------------------------------------");

    }
}