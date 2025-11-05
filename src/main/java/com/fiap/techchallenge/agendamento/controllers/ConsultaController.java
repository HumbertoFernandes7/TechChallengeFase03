package com.fiap.techchallenge.agendamento.controllers;

import com.fiap.techchallenge.agendamento.dtos.ConsultaRequest;
import com.fiap.techchallenge.agendamento.dtos.ConsultaResponse;
import com.fiap.techchallenge.agendamento.entities.ConsultaEntity;
import com.fiap.techchallenge.agendamento.entities.UserEntity;
import com.fiap.techchallenge.agendamento.mappers.ConsultaMapper;
import com.fiap.techchallenge.agendamento.services.ConsultaService;
import com.fiap.techchallenge.agendamento.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;
    private final ConsultaMapper consultaMapper;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    @PostMapping("/create")
    public ResponseEntity<ConsultaResponse> create(@RequestBody @Valid ConsultaRequest consultaRequest){
       UserEntity medico = userService.findById(consultaRequest.getIdMedico());
       UserEntity paciente = userService.findById(consultaRequest.getIdPaciente());
       ConsultaEntity consultaEntity = consultaMapper.toEntity(consultaRequest);
       ConsultaEntity consultaSalva = consultaService.create(consultaEntity, medico, paciente);
       return ResponseEntity.status(HttpStatus.CREATED).body(consultaMapper.toResponse(consultaSalva));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponse> findById(@PathVariable Long id){
        ConsultaEntity consultaEntity = consultaService.findById(id);
        return ResponseEntity.ok(consultaMapper.toResponse(consultaEntity));
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> findAll() {
        return ResponseEntity.ok(consultaMapper.listEntityToListResponse(consultaService.findAll()));
    }

    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponse> update(@PathVariable Long id, @RequestBody @Valid ConsultaRequest consultaRequest) {
        ConsultaEntity consultaEncontrada = consultaService.findById(id);
        UserEntity medico = userService.findById(consultaRequest.getIdMedico());
        UserEntity paciente = userService.findById(consultaRequest.getIdPaciente());
        consultaMapper.copyEntityToOutput(consultaEncontrada, consultaRequest);
        ConsultaEntity consultaAtualizada = consultaService.update(consultaEncontrada, medico, paciente);
        return ResponseEntity.ok(consultaMapper.toResponse(consultaAtualizada));
    }

    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        consultaService.findById(id);
        consultaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    @PatchMapping("/{id}/realizar")
    public ResponseEntity<Void> realizar(@PathVariable Long id) {
        ConsultaEntity consultaEncontrada = consultaService.findById(id);
        consultaService.realizarConsulta(consultaEncontrada);
        return  ResponseEntity.noContent().build();
    }
}