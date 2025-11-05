package com.fiap.techchallenge.agendamento.handlers;

import java.net.http.HttpHeaders;
import java.util.List;
import java.util.stream.Collectors;

import com.fiap.techchallenge.agendamento.exception.BadRequestBusinessException;
import com.fiap.techchallenge.agendamento.exception.NotFoundBusinessException;
import com.fiap.techchallenge.agendamento.exception.UnauthorizedAccessBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestBusinessException.class)
    public final ResponseEntity<ProblemExceptionOutput> handlerBadRequestBussinessException(
            BadRequestBusinessException ex, WebRequest request) {
        ProblemExceptionOutput problema = new ProblemExceptionOutput(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<ProblemExceptionOutput>(problema, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundBusinessException.class)
    public final ResponseEntity<ProblemExceptionOutput> handlerNotFoundBussinessException(NotFoundBusinessException ex, WebRequest request) {
        ProblemExceptionOutput problema = new ProblemExceptionOutput(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<ProblemExceptionOutput>(problema, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedAccessBusinessException.class)
    public final ResponseEntity<ProblemExceptionOutput> handlerNotFoundBussinessException(
            UnauthorizedAccessBusinessException ex, WebRequest request) {
        ProblemExceptionOutput problema = new ProblemExceptionOutput(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return new ResponseEntity<ProblemExceptionOutput>(problema, HttpStatus.UNAUTHORIZED);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<FieldsExceptionOutput> campos = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = (error instanceof FieldError) ? ((FieldError) error).getField() : error.getObjectName();
                    return FieldsExceptionOutput.builder()
                            .name(fieldName)
                            .message(error.getDefaultMessage())
                            .build();
                })
                .collect(Collectors.toList());

        ProblemExceptionOutput problema = new ProblemExceptionOutput(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação. Verifique os campos informados.",
                campos
        );
        return new ResponseEntity<>(problema, HttpStatus.BAD_REQUEST);
    }
}