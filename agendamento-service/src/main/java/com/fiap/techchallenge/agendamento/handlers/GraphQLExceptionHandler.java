package com.fiap.techchallenge.agendamento.handlers;

import com.fiap.techchallenge.agendamento.exception.BadRequestBusinessException;
import com.fiap.techchallenge.agendamento.exception.InvalidCredentialsBusinessException;
import com.fiap.techchallenge.agendamento.exception.NotFoundBusinessException;
import com.fiap.techchallenge.agendamento.exception.UnauthorizedAccessBusinessException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {

        // 1. Traduz nossa exceção de Acesso Negado (a do seu teste)
        if (ex instanceof UnauthorizedAccessBusinessException) {
            return GraphqlErrorBuilder.newError()
                    .errorType(ErrorType.UNAUTHORIZED) // Define o tipo de erro correto
                    .message(ex.getMessage()) // Pega a mensagem: "Você não tem permissão..."
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();
        }

        // 2. Traduz nossa exceção de Não Encontrado (ex: consulta ID 99 não existe)
        if (ex instanceof NotFoundBusinessException) {
            return GraphqlErrorBuilder.newError()
                    .errorType(ErrorType.NOT_FOUND)
                    .message(ex.getMessage()) // Pega a mensagem: "Consulta não encontrada"
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();
        }

        // 3. Traduz nossas exceções de Regra de Negócio (ex: horário já ocupado)
        if (ex instanceof BadRequestBusinessException) {
            return GraphqlErrorBuilder.newError()
                    .errorType(ErrorType.BAD_REQUEST)
                    .message(ex.getMessage()) // Pega a mensagem: "Já existe uma consulta..."
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();
        }

        // 3. Traduz nossas exceções de Regra de Negócio (ex: horário já ocupado)
        if (ex instanceof InvalidCredentialsBusinessException) {
            return GraphqlErrorBuilder.newError()
                    .errorType(ErrorType.UNAUTHORIZED)
                    .message(ex.getMessage()) // Pega a mensagem: "Já existe uma consulta..."
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();
        }

        // 4. Para qualquer outra exceção (Exceptions genéricas),
        //    deixa o Spring tratá-la como INTERNAL_ERROR
        return null;
    }
}
