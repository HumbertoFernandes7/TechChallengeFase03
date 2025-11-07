package com.fiap.techchallenge.notificacoes.services;

import com.fiap.techchallenge.notificacoes.dtos.NotificacaoDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public void sender(NotificacaoDTO notificacaoDTO) {
        try {
            String corpoEmail = "Olá!\n\nUm lembrete foi enviado:\n\n" +
                    "Paciente: " + notificacaoDTO.getNomePaciente() + "\n" +
                    "Data: " + notificacaoDTO.getDataHoraConsulta() + "\n" +
                    "Medico: " + notificacaoDTO.getNomeMedico() + "\n" +
                    "Status: " + notificacaoDTO.getStatus() + "\n\n" +
                    "Atenciosamente,\nEquipe FIAP";

            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setFrom("hnino201333@gmail.com");
            mensagem.setTo(notificacaoDTO.getEmailPaciente());
            mensagem.setSubject(notificacaoDTO.getMensagemNotificacao());
            mensagem.setText(corpoEmail);

            mailSender.send(mensagem);
        } catch (Exception e) {
            logger.error("Falha ao enviar email:" + e.getMessage());
            throw new RuntimeException("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}