package com.fiap.techchallenge.notificacoes.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String CONSULTA_QUEUE_NAME = "fila.consultas.notificacao";

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(CONSULTA_QUEUE_NAME).build();
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}