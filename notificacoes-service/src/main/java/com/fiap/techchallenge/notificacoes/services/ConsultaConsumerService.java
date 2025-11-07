package com.fiap.techchallenge.notificacoes.services;

import com.fiap.techchallenge.notificacoes.dtos.NotificacaoDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultaConsumerService {

    public static final String CONSULTA_QUEUE_NAME = "fila.consultas.notificacao";
    private static final Logger logger = LoggerFactory.getLogger(ConsultaConsumerService.class);

    private final EmailService emailService;

    @RabbitListener(queues = CONSULTA_QUEUE_NAME)
    public void consumirMensagem(NotificacaoDTO notificacao) {
        logger.info("Mensagem recebida da fila. Acionando serviço de e-mail...");
        emailService.sender(notificacao);
    }
}