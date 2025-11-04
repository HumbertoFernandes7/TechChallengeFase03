package com.fiap.techchallenge.agendamento.mappers;

import com.fiap.techchallenge.agendamento.dtos.ConsultaRequest;
import com.fiap.techchallenge.agendamento.dtos.ConsultaResponse;
import com.fiap.techchallenge.agendamento.entities.ConsultaEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConsultaMapper {

    private final ModelMapper modelMapper;

    public ConsultaEntity toEntity(ConsultaRequest consultaRequest) {
        return modelMapper.map(consultaRequest, ConsultaEntity.class);
    }

    public ConsultaResponse toResponse(ConsultaEntity consultaEntity) {
        return modelMapper.map(consultaEntity, ConsultaResponse.class);
    }

    public List<ConsultaResponse> listEntityToListResponse(List<ConsultaEntity> consultas) {
        return consultas.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public void copyEntityToOutput(ConsultaEntity consultaEncontrada, ConsultaRequest consultaRequest) {
        modelMapper.map(consultaRequest, consultaEncontrada);
    }
}