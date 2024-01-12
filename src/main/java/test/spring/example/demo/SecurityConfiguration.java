package test.spring.example.demo;

import static reactor.core.publisher.Mono.just;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerReactiveAuthenticationManagerResolver;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
@ConditionalOnProperty(
        value="auth.enabled",
        havingValue = "true")
public class SecurityConfiguration {

    private static final List<String> WHITELIST = List.of("/actuator/health/**");

    @Autowired
    private AuthenticationExceptionEntryPoint authenticationExceptionEntryPoint;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(WHITELIST.toArray(String[]::new))
                        .permitAll()
                        .anyExchange().authenticated())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationExceptionEntryPoint))
                .oauth2ResourceServer(server -> server.authenticationManagerResolver(reactiveAuthenticationManagerResolver()))
                .build();
    }

    @Bean
    public JwtIssuerReactiveAuthenticationManagerResolver reactiveAuthenticationManagerResolver() {
        return new JwtIssuerReactiveAuthenticationManagerResolver(issuer -> just(new JwtReactiveAuthenticationManager(reactiveJwtDecoder())));
    }

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        return NimbusReactiveJwtDecoder.withJwkSetUri("/auth/realm/demo").build();
    }
}
