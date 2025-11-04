package com.fiap.techchallenge.agendamento.services;

import com.fiap.techchallenge.agendamento.entities.UserEntity;
import com.fiap.techchallenge.agendamento.repositories.UserRepository;
import com.fiap.techchallenge.agendamento.services.validations.user.UserCreateValidation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final List<UserCreateValidation>  validarCadastro;
    private final PasswordEncoder passwordEncoder;

    public UserEntity create(UserEntity user){
        validarCadastro.forEach(v -> v.valida(user));
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        return userRepository.save(user);
    }

    public UserEntity update(UserEntity user){
        return userRepository.save(user);
    }

    public UserEntity findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado no sistema"));
    }

    public UserEntity findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado no sistema"));
    }

    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }
}