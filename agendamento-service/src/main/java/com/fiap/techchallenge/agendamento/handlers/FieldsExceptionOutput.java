package com.fiap.techchallenge.agendamento.handlers;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FieldsExceptionOutput {

    private String name;
    private String message;
}