package com.fiap.techchallenge.agendamento.mappers;

import com.fiap.techchallenge.agendamento.dtos.UserRequest;
import com.fiap.techchallenge.agendamento.dtos.UserResponse;
import com.fiap.techchallenge.agendamento.dtos.UserUpdateRequest;
import com.fiap.techchallenge.agendamento.entities.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserEntity toEntity(UserRequest userRequest) {
        return modelMapper.map(userRequest, UserEntity.class);
    }

    public UserResponse toResponse(UserEntity usuarioSalvo) {
        return modelMapper.map(usuarioSalvo, UserResponse.class);
    }

    public List<UserResponse> listEntityToListResponse(List<UserEntity> users) {
        return users.stream().map(this::toResponse).toList();
    }

    public void copyInputToEntity(UserUpdateRequest updateRequest, UserEntity usuarioEncontrado) {
        modelMapper.map(updateRequest, usuarioEncontrado);
    }
}