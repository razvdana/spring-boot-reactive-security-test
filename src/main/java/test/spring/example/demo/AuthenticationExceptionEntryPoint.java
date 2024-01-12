package test.spring.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationExceptionEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return Mono.fromRunnable(() -> {
            var response = exchange.getResponse();
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
        }).then(Mono.error(ex));
    }
}
