package com.fiap.techchallenge.agendamento.services.validations.user;

import com.fiap.techchallenge.agendamento.entities.UserEntity;

public interface UserCreateValidation {

    void valida(UserEntity user);
}
