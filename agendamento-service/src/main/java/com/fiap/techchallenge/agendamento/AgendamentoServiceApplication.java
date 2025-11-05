package com.fiap.techchallenge.agendamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AgendamentoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendamentoServiceApplication.class, args);
	}

}
