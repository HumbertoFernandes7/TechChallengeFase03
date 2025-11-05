package com.fiap.techchallenge.agendamento.configs;

import com.fiap.techchallenge.agendamento.exception.NotFoundBusinessException;
import com.fiap.techchallenge.agendamento.repositories.UserRepository;
import com.fiap.techchallenge.agendamento.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.recoverToken(request);

        if (token != null) {
            String subject = tokenService.validateToken(token);

            if (!subject.isEmpty()) {
                UserDetails user = userRepository.findByEmail(subject)
                        .orElseThrow(() -> new NotFoundBusinessException("Usuário não encontrado no token"));

                // Cria a autenticação para o Spring Security
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                // Coloca o usuário no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        // Continua a cadeia de filtros
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", ""); // Remove o prefixo "Bearer "
    }
}
