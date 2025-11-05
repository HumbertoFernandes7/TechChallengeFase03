package com.fiap.techchallenge.agendamento.services.validations.user;

import com.fiap.techchallenge.agendamento.entities.UserEntity;
import com.fiap.techchallenge.agendamento.exception.BadRequestBusinessException;
import com.fiap.techchallenge.agendamento.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailExistenteValidation implements UserCreateValidation  {

    private final UserRepository userRepository;

    @Override
    public void valida(UserEntity user) {
        UserEntity usuarioEncontrado = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (usuarioEncontrado != null) {
            throw new BadRequestBusinessException("Email já cadastrado no sistema!");
        }
    }
}