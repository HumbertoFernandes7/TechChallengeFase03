package com.fiap.techchallenge.agendamento.handlers;

import com.fiap.techchallenge.agendamento.exception.BadRequestBusinessException;
import com.fiap.techchallenge.agendamento.exception.InvalidCredentialsBusinessException;
import com.fiap.techchallenge.agendamento.exception.NotFoundBusinessException;
import com.fiap.techchallenge.agendamento.exception.UnauthorizedAccessBusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.security.access.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestBusinessException.class)
    public final ResponseEntity<ProblemExceptionOutput> handlerBadRequestBusinessException(
            BadRequestBusinessException ex, WebRequest request) {
        ProblemExceptionOutput problema = new ProblemExceptionOutput(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<ProblemExceptionOutput>(problema, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundBusinessException.class)
    public final ResponseEntity<ProblemExceptionOutput> handlerNotFoundBusinessException(NotFoundBusinessException ex, WebRequest request) {
        ProblemExceptionOutput problema = new ProblemExceptionOutput(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<ProblemExceptionOutput>(problema, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedAccessBusinessException.class)
    public final ResponseEntity<ProblemExceptionOutput> handlerNotFoundBusinessException(
            UnauthorizedAccessBusinessException ex, WebRequest request) {
        ProblemExceptionOutput problema = new ProblemExceptionOutput(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return new ResponseEntity<ProblemExceptionOutput>(problema, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ProblemExceptionOutput> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request) {
        ProblemExceptionOutput problema = new ProblemExceptionOutput(
                HttpStatus.FORBIDDEN.value(),
                "Acesso negado. Você não tem permissão para executar esta ação."
        );
        return new ResponseEntity<>(problema, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidCredentialsBusinessException.class)
    public final ResponseEntity<ProblemExceptionOutput> handlerInvalidCredentialsBusinesException(
            InvalidCredentialsBusinessException ex, WebRequest request) {
        ProblemExceptionOutput problema = new ProblemExceptionOutput(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return new ResponseEntity<ProblemExceptionOutput>(problema, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ProblemExceptionOutput> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error("Ocorreu um erro interno: " + ex.getMessage(), ex);
        ProblemExceptionOutput problema = new ProblemExceptionOutput(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro interno no servidor. Tente novamente mais tarde.");
        return new ResponseEntity<ProblemExceptionOutput>(problema, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<FieldsExceptionOutput> camposComErro = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> FieldsExceptionOutput.builder()
                        .name(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        ProblemExceptionOutput problema = new ProblemExceptionOutput(
                status.value(),
                "Um ou mais campos estão inválidos",
                camposComErro
        );

        return new ResponseEntity<>(problema, status);
    }
}