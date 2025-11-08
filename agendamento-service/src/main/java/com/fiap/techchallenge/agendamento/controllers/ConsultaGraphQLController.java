package com.fiap.techchallenge.agendamento.controllers;

import com.fiap.techchallenge.agendamento.dtos.ConsultaResponse;
import com.fiap.techchallenge.agendamento.entities.ConsultaEntity;
import com.fiap.techchallenge.agendamento.entities.UserEntity;
import com.fiap.techchallenge.agendamento.mappers.ConsultaMapper;
import com.fiap.techchallenge.agendamento.services.ConsultaService;
import com.fiap.techchallenge.agendamento.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ConsultaGraphQLController {

    private final ConsultaService consultaService;
    private final UserService userService;
    private final ConsultaMapper consultaMapper;

    @QueryMapping
    public List<ConsultaResponse> consultas(@Argument Boolean futuras) {
        List<ConsultaEntity> consultasEncontradas = consultaService.findAll(futuras);
        return consultaMapper.listEntityToListResponse(consultasEncontradas);
    }

    @QueryMapping
    public ConsultaResponse consultaPorId(@Argument Long id) {
        ConsultaEntity consulta = consultaService.findById(id);
        return consultaMapper.toResponse(consulta);
    }
}